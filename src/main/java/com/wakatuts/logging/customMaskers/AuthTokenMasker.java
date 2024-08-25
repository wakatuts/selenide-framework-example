package com.wakatuts.logging.customMaskers;

import tech.petrepopescu.logging.masker.LogMasker;

public class AuthTokenMasker implements LogMasker {

    private char maskChar = '*';

    @Override
    public void setMaskChar(char maskChar) {
        this.maskChar = maskChar;
    }

    @Override
    public int maskData(StringBuilder builder, int startPos, int buffLength) {
        String keyword = "Auth_token:";
        int keywordLength = keyword.length();

        // Check if the keyword "Auth_token:" is at the current position
        if (startPos + keywordLength <= buffLength &&
                builder.substring(startPos, startPos + keywordLength).equals(keyword)) {

            // Find the start of the token value
            int valueStartPos = startPos + keywordLength;

            // Skip any spaces after the keyword
            while (valueStartPos < buffLength && Character.isWhitespace(builder.charAt(valueStartPos))) {
                valueStartPos++;
            }

            // Find the end of the token value (next delimiter or end of buffer)
            int valueEndPos = LogMasker.indexOfNextDelimiter(builder, valueStartPos, buffLength);

            // Replace the token value with the mask character
            for (int i = valueStartPos; i < valueEndPos; i++) {
                builder.setCharAt(i, maskChar);
            }

            // Return the position after the masked value
            return valueEndPos;
        }

        // If no keyword is found, return the original position
        return startPos;
    }

}
