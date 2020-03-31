<p align="center">
<img align="center" src="./slide_to_confirm.gif" />
</p>


# Slide to confirm
### What's this?
- It is a component that requires user slide to confirm for some proceeding actions
- Works on Android

### How is it implemented?
- It extends the Android `RelativeLayout`
- It adds necessary children at runtime
- It detects user drags on the slider
- It detects the user drags the slider to the end, and vibrate according to configuration
- It resets its status when user releases but the slider is not at the end

### How to use?
- It's easy:
