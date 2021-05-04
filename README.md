# ViewWorld
自定义View合集，开启自定义View的神奇之旅.

# TKBanner
自定义无限轮播滚动控件
## 亮点
* **支持无限轮播，支持自定义UI和指示符，高度解耦**
* **默认支持圆点和数字指示符**

## 截图

| 仿知乎日报APP轮播图 | 仿品玩APP轮播图 | 仿虎嗅APP轮播图 |
|:-:|:-:|:-:|
|![仿知乎日报APP轮播图](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/banner_zhihu_daily.webp)|![仿品玩APP轮播图](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/banner_pingwest.webp)|![仿虎嗅APP轮播图](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/banner_huxiu.webp)

# 相关属性
| 属性名称 | 类型 | 说明 |
|:-|:-|:-|
|banner_autoPlayAble|boolean|是否自动轮播，默认为true|
|banner_autoPlayInterval|integer|轮播时间间隔，默认为3000毫秒|
|banner_showIndicator|boolean|是否显示指示符，默认为true，如设置为false，则使用自定义的指示符|
|banner_pointContainerLeftRightPadding|dimension|底部圆点指示符容器左右Padding|
|banner_pointContainerTopBottomPadding|dimension|底部圆点指示符容器上下Padding|
|banner_pointLeftRightMargin|dimension|圆点指示符左右Margin|
|banner_pointTopBottomMargin|dimension|圆点指示符上下Margin|
|banner_pointDrawable|reference|圆点指示符对应的drawable Id|
|banner_isNumberIndicator|boolean|是否为数字指示符，默认为false|
|banner_numberIndicatorTextColor|reference|color|数字指示符文本颜色|
|banner_numberIndicatorTextSize|dimension|数字指示符文本字体大小|
|banner_numberIndicatorBackground|reference|数字指示符背景|

# VerificationCodeEditText

## 亮点
* **支持自定义验证码背景，可根据需求自定义每个验证码背景，高度解耦**
* **支持自定义光标颜色和宽度，支持光标隐藏或闪烁**

## 截图

| 直线形验证码 | 方形验证码 |
|:-:|:-:|
|![image](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/1.gif)|![image](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/2.gif)|

## 相关属性
| 属性名称 | 类型 | 说明 |
|:-|:-|:-|
|codeLength|integer|验证码总个数，默认为6|
|codeWidth|dimension|每个验证码的宽度，默认为150px,高度和宽度相同，仅当android:layout_width="wrap_parent"时起作用，当 android:layout_width="match_parent"时每一个验证码的宽度由动态计算获取，其值为(控件总宽度-(验证码总个数-1)*验证码之间的间隔)/验证码总个数|
|codeMargin|dimension|验证码之间的间隔，默认为20px|
|codeBackground|reference|每个验证码的背景,必须项|
|codeCursorVisible|boolean|是否显示光标，默认为false，即不显示光标|
|codeCursorDrawable|reference|光标背景，仅当codeCursorVisible为true起作用，默认为颜色值为colorAccent，宽度为1dp的GradientDrawable|

## 使用
* 直线形验证码
```xml
<com.github.kongpf8848.viewworld.views.VerificationCodeEditText
    android:id="@+id/et_verification_code"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="40dp"
    android:layout_marginTop="50dp"
    android:layout_marginEnd="40dp"
    android:cursorVisible="true"
    android:inputType="number"
    android:singleLine="true"
    android:textColor="@color/black"
    android:textSize="40sp"
    app:codeBackground="@drawable/bg_code_edit_line"
    app:codeCursorDrawable="@drawable/bg_code_edit_cursor"
    app:codeCursorVisible="true"
    app:codeLength="4"
    app:codeMargin="10dp" />
```
  app:codeBackground定义每个验证码的背景，其中<code>android:state_selected="true"</code>为选中状态下的背景，如：
  ```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true">
        <layer-list>
            <item android:gravity="bottom">
                <shape>
                    <solid android:color="#FFDE00" />
                    <size android:height="2dp" />
                </shape>
            </item>
        </layer-list>
    </item>
    <item>
        <layer-list>
            <item android:gravity="bottom">
                <shape>
                    <solid android:color="#f4f4f4" />
                    <size android:height="2dp" />
                </shape>
            </item>
        </layer-list>
    </item>
</selector>
  ```
  app:codeCursorDrawable定义光标颜色，如:
  ```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#FFDE00" />
    <size android:width="2dp" />
</shape>
  ```
 * 方形验证码
 ```xml
<com.github.kongpf8848.viewworld.views.VerificationCodeEditText
    android:id="@+id/et_verification_code"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="40dp"
    android:layout_marginEnd="40dp"
    android:layout_marginTop="50dp"
    android:inputType="number"
    android:singleLine="true"
    android:textSize="20sp"
    android:textColor="@color/black"
    app:codeBackground="@drawable/bg_code_edit_square"
    app:codeLength="6"
    app:codeMargin="10dp"
    app:codeCursorVisible="true"
    app:codeCursorDrawable="@drawable/bg_code_edit_cursor"
    />

 ```
 app:codeBackground定义每个验证码的背景，其中<code>android:state_selected="true"</code>为选中状态下的背景，如：
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true">
        <shape>
            <stroke android:width="2dp" android:color="#FFFB9C00" />
            <corners android:radius="4dp" />
        </shape>
    </item>
    <item>
        <shape>
            <stroke android:width="2dp" android:color="#EEEEEE" />
            <corners android:radius="4dp" />
        </shape>
    </item>
</selector>
 ```
app:codeCursorDrawable定义光标颜色，如:
  ```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="#FFDE00" />
    <size android:width="2dp" />
</shape>
  ```
# TabLayoutEx

## 亮点

 * **基于原生TabLayout源码修改而来，支持原TabLayout所有功能，用法也基本保持一致，坚定而固执的认为原生的往往是最好的:smile:**
 * **取消原生TabLayout默认将文字转换为大写的属性**
 * **添加选中字体变大和加粗效果**
 * **添加Tab圆角背景动画，支持背景越界回弹效果**
 * **添加指示符跳跃动画**
 
## 截图
![image](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/TabLayoutEx.webp)

# 相关属性
**TabLayout原有的属性基本都支持,此处仅列出新添加的属性**
| 属性名称 | 类型 | 说明 |
|:-|:-|:-|
|tabUnSelectedTextSize|dimension|未选中字体大小|
|tabSelectedTextSize|dimension|选中字体大小|
|tabBoldWhenSelected|boolean|选中字体是否加粗|
|tabBackgroundIsCorner|boolean|是否使用圆角背景|
|tabSlideAnimType|enum|跳跃动画样式,none表示不启用跳跃动画,half_glue表示启用跳跃动画1,glue表示启用跳跃动画2|

**TabLayoutEx和原生TabLayout功能相同但名字有修改的属性**
* tabMode改为tabModeEx
* tabGravity改为tabGravityEx
* tabIconTintMode改为tabIconTintModeEx
* tabIndicatorGravity改为tabIndicatorGravityEx

## 用法
```xml
<com.github.kongpf8848.viewworld.views.TabLayoutEx
    android:id="@+id/tab_layout"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:layout_marginTop="10dp"
    android:background="@color/white"
    <!--每个TabView的左边距-->
    app:tabPaddingStart="10dp"
    <!--每个TabView的右边距-->
    app:tabPaddingEnd="10dp"
    <!--SlidingTabIndicator的左边距，其值=app:tabPaddingStart+实际的左边距-->
    app:tabContentStart="25dp"
    <!--tab模式，scrollable或fixed-->
    app:tabModeEx="scrollable"
    <!--指示符和TabView宽度是否相同-->
    app:tabIndicatorFullWidth="true"
    <!--指示符高度-->
    app:tabIndicatorHeight="32dp"
    <!--未选中文字颜色-->
    app:tabTextColor="#999999"
    <!--选中文字颜色-->
    app:tabSelectedTextColor="@color/black"
    <!--点击波纹颜色，透明即去除波纹-->
    app:tabRippleColor="@color/transparent"
    <!--未选中文字大小-->
    app:tabUnSelectedTextSize="14sp"
    <!--选中文字大小-->
    app:tabSelectedTextSize="16sp"
    <!--是否为圆角背景-->
    app:tabBackgroundIsCorner="true"
    <!--选中字体是否加粗-->
    app:tabBoldWhenSelected="true"
    />
```

