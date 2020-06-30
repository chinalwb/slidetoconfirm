
<p align="center">
<img align="center" src="./slide_to_confirm.gif" />
</p>


# 滑动确认组件   [ ![Download](https://api.bintray.com/packages/chinalwb/slidetoconfirm/slidetoconfirm/images/download.svg) ](https://bintray.com/chinalwb/slidetoconfirm/slidetoconfirm/_latestVersion)

### 这是什么?
- 当你的 App 需要用户滑动来确认某些信息的时候，可以使用这个滑动确认组件
- 适用 Android 平台

### 长什么样?
![直角边框](https://user-images.githubusercontent.com/1758864/78349273-efce9b00-75d5-11ea-9a10-8022db9ade5a.png)

![圆角边框](https://user-images.githubusercontent.com/1758864/78349279-f230f500-75d5-11ea-9679-d3f400cd7135.png)

![圆形边框](https://user-images.githubusercontent.com/1758864/78349282-f52be580-75d5-11ea-8ca7-26531129b171.png)

![UI_4](https://user-images.githubusercontent.com/1758864/78350196-5dc79200-75d7-11ea-9971-6f0a0799d21b.png)

![UI_5](https://user-images.githubusercontent.com/1758864/78350204-6029ec00-75d7-11ea-8f1c-4da0558f5337.png)

![samples](https://user-images.githubusercontent.com/1758864/86143658-4b51eb80-bb27-11ea-9873-e3d00209ce6d.gif)


### 内部怎么实现的?
- 继承自 Android `RelativeLayout`
- 在运行时根据需要添加必要的子控件
- 监听用户的拖动操作
- 当监听到用户拖动到最右端的时候，可根据配置来震动提醒
- 如果用户松手的时候滑块没有在最右端，自动滑动到最左端

### 有什么好的地方？
- 非常简单易用，一行代码搞定所有配置
- 不做无用操作，性能高效
- 合理处理跟外层 view 垂直方向的滑动冲突
- 滑块 UI 自定义，可以使用图片或 Lottie / 边框圆角半径 / 背景色 / 动画时间 / 震动提醒 等
- 允许状态重置, 轻松解锁多步确认操作！

### 如何使用?
- 简单两步:
1. 在 build.gradle 中加依赖 `implementation 'com.github.chinalwb:slidetoconfirm:1.0.2'`
2. 然后在需要的地方引用 `com.chinalwb.slidetoconfirmlib.SlideToConfirm`, 比如:
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

### 有哪些可配置的选项?
|   配置名称   |   描述   |  示例参数值    |
| ---- | ---- | ---- |
|   border_radius   |  整个控件边框的圆角半径    |   0dp / 2dp /  xx dp   |
|   border_width   |   边框的厚度    |   1dp   |
|   slider_background_color   |  整个控件的背景色    |  任何色值    |
|   slider_color   |   滑块的颜色  |   任何色值    |
|   slider_lottie   |   Lottie 资源文件名称    |   比如 lottie_x.json -- 需要放到 `assets` 目录下    |
|   slider_image   |   如果不用 Lottie, 可以使用图片, 如果这个属性和 `slider_lottie` 都设置了，这个属性生效，lottie 失效    |   图片的引用   |
|   slider_width   |  滑块的宽度   |  100dp    |
|   slider_reset_duration   |  拖动滑块松手时，如果滑块没有在最右端，恢复动画的时间，单位是 `ms`    |   300   |
|   slider_vibration_duration   |  拖动滑块时，如果拖动到最右端松手时震动时长，单位是 `ms`   |  100    |
|   slider_threshold   |  检测是否滑动到最右端的偏差值, 比如 0dp 意味着必须滑动到最右端才算有效    |  1dp    |
|   engage_text   |  滑动组件的提示文字 `滑动以解锁`    |  任何字符串，比如 `滑动以解锁`    |
|   engage_text_size   |   滑动组件的提示文字的字体大小    |  17sp    |
|   engage_text_color   |  滑动组件的提示文字的字体颜色    |  任何色值    |
|   engaged_text_font   |  滑动组件的提示文字的字体样式, 需要放到 `res/font` 目录下    |  比如 @font/你的字体资源文件    |
|   completed_text   |  滑动成功之后的提示文字. 比如: `解锁成功!`    |   任何字符串  |
|   completed_text_size   |   滑动成功之后的提示文字的字体大小   |  17sp    |
|   completed_text_color   |  滑动成功之后的提示文字的字体颜色    |  任何色值    |
|   completed_text_font   |   滑动成功之后的提示文字的字体样式, 需要放到 `res/font` 目录下      |  @font/你的字体资源文件    |


### 可能用的到的 API
1. `SlideToConfirm.setSlideListener(new ISlideListener() { ... });` 当需要监听滑动状态的时候用这个。比如开始滑动 、 正在滑动、松手取消 、 松手完成 这些事件均在这个接口中提供了回调方法。
2. `SlideToConfirm.reset();` 当需要在已经完成的状态下重置组件状态的时候，调用这个方法。
3. `SlideToConfirm.setEngageText(...)`; 当需要用代码设定滑动组件显示文字的时候，调用这个方法。
4. `SlideToConfirm.setCompletedText(...)`; 当需要用代码设定滑动完成显示文字的时候，调用这个方法。

------
License: MIT


