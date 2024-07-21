package com.wakatuts.pages;

import com.google.inject.Inject;
import com.wakatuts.pages.components.MenuBar;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class BasePage {

    @Inject @Getter private MenuBar menuBar;

    public BasePage() {
    }

}
