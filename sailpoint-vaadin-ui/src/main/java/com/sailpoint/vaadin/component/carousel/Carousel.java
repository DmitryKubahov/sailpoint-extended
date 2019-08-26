package com.sailpoint.vaadin.component.carousel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.shared.Registration;

/**
 * Java component for slider
 */
@Tag("l2t-paper-slider")
@NpmPackage(value = "@xpertsea/paper-slider", version = "3.0.0")
@JsModule("@xpertsea/paper-slider/l2t-paper-slider.js")
@CssImport(value = "./js/carousel.css")
public class Carousel extends Component implements HasSize, HasStyle {

    /**
     * Custom carousel style name
     */
    private static final String CUSTOM_CAROUSEL_STYLE_NAME = "custom-carousel-style";

    private Slide[] slides;
    private boolean autoProgress;
    private int slideDuration = 2;
    private int startPosition;
    private boolean disableSwipe;
    private boolean hideNavigation;

    public Carousel(Slide... paperSlides) {
        this.setSlides(paperSlides);
        updateSlides(paperSlides);
        addClassName(CUSTOM_CAROUSEL_STYLE_NAME);
    }

    private void updateSlides(Slide... paperSlides) {
        for (Slide slide : paperSlides) {
            this.getElement().appendChild(slide.getElement());
        }
        updateProperties();
    }

    private void updateProperties() {
        this.getElement().setAttribute("auto-progress", Boolean.toString(autoProgress));
        this.getElement().setAttribute("disable-swipe", Boolean.toString(disableSwipe));
        this.getElement().setAttribute("hide-nav", hideNavigation);
        this.getElement().setAttribute("slide-duration", "" + this.slideDuration);
        this.getElement().setAttribute("position", "" + this.startPosition);
    }

    // PROPERTIES
    public Slide[] getSlides() {
        return slides;
    }

    public void setSlides(Slide[] slides) {
        this.slides = slides;
        updateSlides(slides);
    }

    public boolean isAutoProgress() {
        return autoProgress;
    }

    public void setAutoProgress(boolean autoProgress) {
        this.autoProgress = autoProgress;
    }

    public int getSlideDuration() {
        return slideDuration;
    }

    public void setSlideDuration(int slideDuration) {
        this.slideDuration = slideDuration;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public boolean isDisableSwipe() {
        return disableSwipe;
    }

    public void setDisableSwipe(boolean disableSwipe) {
        this.disableSwipe = disableSwipe;
    }

    public boolean isHideNavigation() {
        return hideNavigation;
    }

    public void setHideNavigation(boolean hideNavigation) {
        this.hideNavigation = hideNavigation;
    }

    // FLUENT API
    public Carousel withAutoProgress() {
        this.autoProgress = true;
        updateProperties();
        return this;
    }

    public Carousel withoutSwipe() {
        this.disableSwipe = true;
        updateProperties();
        return this;
    }

    public Carousel withoutNavigation() {
        this.hideNavigation = true;
        updateProperties();
        return this;
    }

    public Carousel withSlideDuration(int slideDuration) {
        this.slideDuration = slideDuration;
        updateProperties();
        return this;
    }

    public Carousel withStartPosition(int startPosition) {
        this.startPosition = startPosition;
        updateProperties();
        return this;
    }

    @Override
    public String getHeight() {
        return getElement().getStyle().get("--paper-slide-height");
    }

    // SIZING
    @Override
    public void setHeight(String height) {
        getElement().getStyle().set("--paper-slide-height", height);
    }

    // METHODS

    /**
     * Move to the next slide
     */
    public void moveNext() {
        this.getElement().callJsFunction("moveNext");
    }

    /**
     * Move to the previous slide
     */
    public void movePrev() {
        this.getElement().callJsFunction("movePrev");
    }

    /**
     * Move to a specific slide
     *
     * @param slide
     */
    public void movePos(int slide) {
        this.getElement().callJsFunction("movePos", "" + slide);
    }

    public Registration addChangeListener(ComponentEventListener<SlideChangeEvent> listener) {
        return addListener(SlideChangeEvent.class, listener);
    }

    // EVENTS
    @DomEvent("position-changed")
    static public class SlideChangeEvent extends ComponentEvent<Carousel> {
        public SlideChangeEvent(Carousel source, boolean fromClient) {
            super(source, true);
        }
    }


}