package com.wakatuts.logging;

import com.wakatuts.logging.customMaskers.AuthTokenMasker;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import tech.petrepopescu.logging.MaskingConverter;
import tech.petrepopescu.logging.masker.PasswordMasker;

import java.util.*;

public class MaskedAllureRestAssured implements OrderedFilter {

    private static final String HIDDEN_PLACEHOLDER = "[ BLACKLISTED ]";
    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";
    private String requestAttachmentName = "Request";
    private String responseAttachmentName;

    public MaskedAllureRestAssured() {
    }

    public MaskedAllureRestAssured setRequestTemplate(String templatePath) {
        this.requestTemplatePath = templatePath;
        return this;
    }

    public MaskedAllureRestAssured setResponseTemplate(String templatePath) {
        this.responseTemplatePath = templatePath;
        return this;
    }

    public MaskedAllureRestAssured setRequestAttachmentName(String requestAttachmentName) {
        this.requestAttachmentName = requestAttachmentName;
        return this;
    }

    public MaskedAllureRestAssured setResponseAttachmentName(String responseAttachmentName) {
        this.responseAttachmentName = responseAttachmentName;
        return this;
    }

    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext filterContext) {
        Prettifier prettifier = new Prettifier();
        String url = requestSpec.getURI();
        Set<String> hiddenHeaders = new TreeSet(String.CASE_INSENSITIVE_ORDER);

        String originalBody = requestSpec.getBody().toString();

        MaskingConverter masker = new MaskingConverter();
        masker.setMaskers(Arrays.asList(new PasswordMasker(new String[] {"cookie", "password"})));
        StringBuilder sb = new StringBuilder(originalBody);
        masker.mask(sb);
        String body = sb.toString();

        requestSpec.body(sb.toString());

        hiddenHeaders.addAll((Collection)Objects.requireNonNull(requestSpec.getConfig().getLogConfig().blacklistedHeaders()));
        HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder.create(this.requestAttachmentName, url).setMethod(requestSpec.getMethod()).setHeaders(toMapConverter(requestSpec.getHeaders(), hiddenHeaders)).setCookies(toMapConverter(requestSpec.getCookies(), new HashSet()));
        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        requestSpec.body(originalBody);

        if (Objects.nonNull(requestSpec.getFormParams())) {
            requestAttachmentBuilder.setFormParams(requestSpec.getFormParams());
        }

        HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        (new DefaultAttachmentProcessor()).addAttachment(requestAttachment, new FreemarkerAttachmentRenderer(this.requestTemplatePath));
        Response response = filterContext.next(requestSpec, responseSpec);

        String originalResponseBody = response.getBody().asPrettyString();
        if(originalResponseBody.contains("token")) {
            originalResponseBody = originalResponseBody.replaceAll("\"", "");
        }

        MaskingConverter masker2 = new MaskingConverter();
        masker2.setMaskers(Arrays.asList(new AuthTokenMasker()));
        StringBuilder sb2 = new StringBuilder(originalResponseBody);
        masker2.mask(sb2);
        String maskedBody = sb2.toString();
        Response maskedResponse = new ResponseBuilder().clone(response).setBody(maskedBody).build();

        String attachmentName = (String)Optional.ofNullable(this.responseAttachmentName).orElse(response.getStatusLine());
        HttpResponseAttachment responseAttachment = io.qameta.allure.attachment.http.HttpResponseAttachment.Builder.create(attachmentName).setResponseCode(response.getStatusCode()).setHeaders(toMapConverter(response.getHeaders(), hiddenHeaders)).setBody(prettifier.getPrettifiedBodyIfPossible(maskedResponse, maskedResponse.getBody())).build();
        (new DefaultAttachmentProcessor()).addAttachment(responseAttachment, new FreemarkerAttachmentRenderer(this.responseTemplatePath));
        return response;
    }

    private static Map<String, String> toMapConverter(Iterable<? extends NameAndValue> items, Set<String> toHide) {
        Map<String, String> result = new HashMap();
        items.forEach((h) -> {
            result.put(h.getName(), toHide.contains(h.getName()) ? "[ BLACKLISTED ]" : h.getValue());
        });
        return result;
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
