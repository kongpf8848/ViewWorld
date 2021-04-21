# ViewWorld
自定义View合集，开启自定义View的神奇之旅

# 验证码输入框

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
 * **添加选中字体变大和加粗效果**
 * **添加Tab圆角背景动画，支持背景越界回弹效果**
 * **添加指示符跳跃动画**
 * **添加设置文字左右padding功能**
 
## 截图
![image](https://github.com/kongpf8848/ViewWorld/blob/master/screenshots/TabLayoutEx.webp)
  
