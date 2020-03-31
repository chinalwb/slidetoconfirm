<p align="center">
<img align="center" src="./slide_to_confirm.gif" />
</p>


# Slide to confirm
### What's this?
- It is a component that requires user slide to confirm for some proceeding actions
- Works on Android

### What it looks like?
- Various UI looking screenshots

### How is it implemented?
- It extends the Android `RelativeLayout`
- It adds necessary children at runtime
- It detects user drags on the slider
- It detects the user drags the slider to the end, and vibrate according to configuration
- It resets its status when user releases but the slider is not at the end

### Highlights
- Easy to use, one line code for all configurations
- Perfect performance
- Deal with conflicts between vertical scroll guestures on out container perfectly
- Flexible for customizing the slider / border radius / background colors / animate duration / vibrate duration / ...

### How to use?
- Two steps:
1. Add `implementation 'com.github.chinalwb:slidetoconfirm:1.0.0'` in your build.gradle
2. Include `com.chinalwb.slidetoconfirmlib.SlideToConfirm` whenever necessary

### How do I customize?
- All customizable attributes
