<p align="center">
<img align="center" src="./slide_to_confirm.gif" />
</p>


# Slide to confirm
### What's this?
----------
- It is a component that requires user slide to confirm for some proceeding actions
- Works on Android

### What does it look like?
----------
![Sharp corners](https://user-images.githubusercontent.com/1758864/78349273-efce9b00-75d5-11ea-9a10-8022db9ade5a.png)

![Round corners](https://user-images.githubusercontent.com/1758864/78349279-f230f500-75d5-11ea-9679-d3f400cd7135.png)

![Circle](https://user-images.githubusercontent.com/1758864/78349282-f52be580-75d5-11ea-8ca7-26531129b171.png)

![UI_4](https://user-images.githubusercontent.com/1758864/78350196-5dc79200-75d7-11ea-9971-6f0a0799d21b.png)

![UI_5](https://user-images.githubusercontent.com/1758864/78350204-6029ec00-75d7-11ea-8f1c-4da0558f5337.png)

### How is it implemented?
----------
- It extends the Android `RelativeLayout`
- It adds necessary children at runtime
- It detects user drags on the slider
- It detects the user drags the slider to the end, and vibrate according to configuration
- It resets its status when user releases but the slider is not at the end

### Highlights
----------
- Easy to use, one line code for all configurations
- Perfect performance
- Deal with conflicts between vertical scroll guestures on out container perfectly
- Flexible for customizing the slider (either LOTTIE or image) / border radius / background colors / animate duration / vibrate duration / ...

### How to use?
----------
- Two steps:
1. Add `implementation 'com.github.chinalwb:slidetoconfirm:1.0.0'` in your build.gradle
2. Include `com.chinalwb.slidetoconfirmlib.SlideToConfirm` whenever necessary, for example:
```

<com.chinalwb.slidetoconfirmlib.SlideToConfirm xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/slide_to_confirm"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:layout_gravity="center_vertical"
    android:layout_margin="20dp"

    app:border_radius="30dp"
    app:border_width="2dp"
    app:slider_background_color="@color/purple"
    app:slider_color="@color/colorAccent"
    app:slider_lottie="lottie_x.json"
    app:slider_image="@drawable/slider"
    app:slider_width="100dp"
    app:slider_reset_duration="1000"
    app:slider_vibration_duration="50"
    app:slider_threshold="1dp"
    app:engage_text="Your action text"
    app:engage_text_size="17sp"
    app:engage_text_color="@android:color/white"
    app:engaged_text_font="@font/solid"
    app:completed_text="Unlocked!"
    app:completed_text_color="@android:color/white"
    app:completed_text_size="30sp"

    tools:context=".MainActivity" >
</com.chinalwb.slidetoconfirmlib.SlideToConfirm>
```

### How do I customize?
----------
|   Attribute Name   |   Description   |  Sample Value    |
| ---- | ---- | ---- |
|   border_radius   |  The border radius of the entire layout    |   0dp / 2dp / any other dp   |
|   border_width   |   The width of the border    |   1dp   |
|   slider_background_color   |  The color of the entire layout    |  any color referrence    |
|   slider_color   |   The color of the slider  |   any color referrence    |
|   slider_lottie   |   The lottie file to be shown as slider    |   lottie_x.json -- needs to be in `assets` folder    |
|   slider_image   |   If you don't want to use lottie, you can use image, when both this and `slider_lottie` are provided, this wins    |   any drawable referrene   |
|   slider_width   |  Specify what the size of the slider   |  100dp    |
|   slider_reset_duration   |  How long it takes for the reset animation when releasing the slider if not at end, in `ms`    |   300   |
|   slider_vibration_duration   |  How long for the vibration when triggering unlocked, in `ms`   |  100    |
|   slider_threshold   |  How far you'd regard the user has reached the end, for example 0dp means user has to explict reaches the right end    |  1dp    |
|   engage_text   |  To customize `Slide to unlock`    |  any string, like `slide to xxx`    |
|   engage_text_size   |  The text size of the engage text `Slide to unlock`    |  17sp    |
|   engage_text_color   |  The text color of the engage text `Slide to unlock`    |  any color referrence     |
|   engaged_text_font   |  The text font of the engage text, in `res/font` folder    |  @font/your_font    |
|   completed_text   |  To customize the text after unlocked. e.g.: `Unlocked!`    |   any string you want  |
|   completed_text_size   |   The text size of the completed text   |  17sp    |
|   completed_text_color   |  The text color of the completed text    |  any color referrence    |
|   completed_text_font   |   The text font of the completed text, in `res/font` folder      |  @font/your_font    |


------
License: MIT

If you find my work is helpful to you or you are start using my code, you don't need to buy me a coffee, just could you please send me a "✨"? Your * encourages me to make more features open source, thanks for your support. You can contact me at 329055754@qq.com if you need any customization or any suggestion.


