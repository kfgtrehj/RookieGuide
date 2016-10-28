# RookieGuide

## RookieGuide简介
主要用作新新手引导。
高亮某一控件，并在该被高亮控件的旁边添加解释或其他内容。

## 使用Gradle添加依赖
在工程root目录build.gradle中添加maven仓库地址
```javascript
allprojects {
		repositories {
      jcenter()
			...
			maven { url "https://jitpack.io" }
		}
}
```
在项目的build.gradle中添加依赖地址
```
dependencies {
    ...
	  compile 'com.github.kfgtrehj:RookieGuide:1.1'
}
```

## 使用方法
```java
RookieContextVLP rookieContextVLP = new RookieContextVLP(R.mipmap.rookie_login_tips, ViewGravity.TOP);
new RookieGuide.Builder(this)
  .setTarget(this.radio_me)
  .setPositiveRes(R.mipmap.positive_button)
  .addTipsView(rookieContextVLP)
  .show();
```
