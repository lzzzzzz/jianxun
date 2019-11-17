# 前端在编程过程中对I浏览器兼容的总结
## 一、bootstrap
`Internet Explorer 8 和 9 是被支持的，然而，你要知道，很多 CSS3 属性和 HTML5 元素 -- 例如，圆角矩形和投影 -- 是肯定不被支持的。另外， Internet Explorer 8 需要 Respond.js 配合才能实现对媒体查询（media query）的支持。`
### Internet Explorer 8 与 Respond.js
在开发环境和生产（线上）环境需要支持 Internet Explorer 8 时，请务必注意下面给出的警告。
### Respond.js 与 跨域（cross-domain） CSS 的问题
如果 Respond.js 和 CSS 文件被放在不同的域名或子域名下面（例如，使用CDN）时需要一些额外的设置。请参考 [Respond.js](https://github.com/scottjehl/Respond/blob/master/README.md#cdnx-domain-setup) 文档 获取详细信息。

`对此：我们要求将bootstrap以及Respond.js的相关js和css下载到项目中，不使用远程的CDN`

### Respond.js 与 `file://` 协议
由于浏览器的安全机制，Respond.js 不能在通过 `file://` 协议（打开本地HTML文件所用的协议）访问的页面上发挥正常的功能。如果需要测试 IE8 下面的响应式特性，务必通过 http 协议访问页面（例如搭建 apache、nginx 等）。请参考 [Respond.js](https://github.com/scottjehl/Respond/blob/master/README.md#support--caveats) 文档获取更多信息。

### Respond.js 与 `@import` 指令
Respond.js 不支持通过 `@import` 指令所引入的 CSS 文件。例如，Drupal 一般被配置为通过 @import 指令引入CSS文件，Respond.js 对其将无法起到作用。请参考 [Respond.js](https://github.com/scottjehl/Respond/blob/master/README.md#support--caveats) 文档获取更多信息。

### Internet Explorer 8 与 box-sizing 属性
当 `box-sizing: border-box;` 与 `min-width`、`max-width`、`min-height` 或 `max-height` 一同使用时，IE8 不能完全支持 box-sizing 属性。由于此原因，从 Bootstrap v3.0.1 版本开始，我们不再为 `.container` 赋予 `max-width` 属性。

### Internet Explorer 8 与 @font-face
当 `@font-face` 与 `:before` 在 IE8 下共同使用时会出现问题。由于 Bootstrap 对 Glyphicons 的样式设置使用了这一组合方式，如果某个页面被浏览器缓存了，并且此页面不是通过点击“刷新”按钮或通过 iframe 加载的，那么就会导致字体文件尚未加载的情况下就开始绘制此页面。当鼠标滑过页面（body）时，页面上的某些图标就会显现，鼠标滑过其他没有显现的图标时，这些图标就能显示出来了。请参考 [issue #13863](https://github.com/twbs/bootstrap/issues/13863) 了解详细信息。

### IE 兼容模式
Bootstrap 不支持 IE 古老的兼容模式。为了让 IE 浏览器运行最新的渲染模式下，建议将此 <meta> 标签加入到你的页面中：
```html
<meta http-equiv="X-UA-Compatible" content="IE=edge">
```
### 
国内浏览器厂商一般都支持兼容模式（即 IE 内核）和高速模式（即 webkit 内核），不幸的是，所有国产浏览器都是默认使用兼容模式，这就造成由于低版本 IE （IE8 及以下）内核让基于 Bootstrap 构建的网站展现效果很糟糕的情况。幸运的是，国内浏览器厂商逐渐意识到了这一点，某些厂商已经开始有所作为了！

将下面的 `<meta>` 标签加入到页面中，可以让部分国产浏览器默认采用高速模式渲染页面：
```html
<meta name="renderer" content="webkit">
```
目前只有[360浏览器](http://se.360.cn/v6/help/meta.html)支持此 `<meta>` 标签。希望更多国内浏览器尽快采取行动、尽快进入高速时代！

### Windows 8 中的 Internet Explorer 10 和 Windows Phone 8
Internet Explorer 10 并没有对 **屏幕的宽度** 和 **视口（viewport）的宽度** 进行区分，这就导致 Bootstrap 中的媒体查询并不能很好的发挥作用。为了解决这个问题，你可以引入下面列出的这段 CSS 代码暂时修复此问题：
```css
@-ms-viewport       { width: device-width; }
```
然而，这样做并不能对 Windows Phone 8 [Update 3 (a.k.a. GDR3)](http://blogs.windows.com/windows_phone/b/wpdev/archive/2013/10/14/introducing-windows-phone-preview-for-developers.aspx) 版本之前的设备起作用，由于这样做将导致 Windows Phone 8 设备按照桌面浏览器的方式呈现页面，而不是较窄的“手机”呈现方式，为了解决这个问题，还需要**加入以下 CSS 和 JavaScript 代码来化解此问题。**
```css
@-ms-viewport       { width: device-width; }
@-o-viewport        { width: device-width; }
@viewport           { width: device-width; }
```
```javascript
// Copyright 2014-2015 Twitter, Inc.
// Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
  var msViewportStyle = document.createElement('style')
  msViewportStyle.appendChild(
    document.createTextNode(
      '@-ms-viewport{width:auto!important}'
    )
  )
  document.querySelector('head').appendChild(msViewportStyle)
}
```
请查看 [Windows Phone 8 and Device-Width](http://timkadlec.com/2013/01/windows-phone-8-and-device-width/) 以了解更多信息。

作为提醒，我们将上面的代码加入到了所有 Bootstrap 文档和实例页面中。

### Safari 对百分比数字凑整的问题
OS X 上搭载的 v7.1 以前 Safari 和 iOS v8.0 上搭载的 Safari 浏览器的绘制引擎对于处理 `.col-*-1` 类所对应的很长的百分比小数存在 bug。也就是说，如果你在一行（row）之中定义了12个单独的列（.col-*-1），你就会看到这一行比其他行要短一些。除了升级 Safari/iOS 外，有以下几种方式来应对此问题：
> * 为最后一列添加 `.pull-right` 类，将其暴力向右对齐
> * 手动调整百分比数字，让其针对Safari表现更好（这比第一种方式更困难）

### 模态框、导航条和虚拟键盘
#### 超出范围和滚动
`<body>` 元素在 iOS 和 Android 上对 `overflow: hidden` 的支持很有限。结果就是，在这两种设备上的浏览器中，当你滚动屏幕超过模态框的顶部或底部时，`<body>` 中的内容将开始随着滚动。See [Chrome bug #175502](https://bugs.chromium.org/p/chromium/issues/detail?id=175502) (fixed in Chrome v40) and [WebKit bug #153852](https://bugs.webkit.org/show_bug.cgi?id=153852).
#### iOS text fields and scrolling
As of iOS 9.3, while a modal is open, if the initial touch of a scroll gesture is within the boundary of a textual `<input>` or a `<textarea>`, the `<body>` content underneath the modal will be scrolled instead of the modal itself. See WebKit bug #153856.
#### 虚拟键盘
还有，如果你正在使用 fixed 定位的导航条或在模态框上面使用输入框，还会遇到 iOS 在页面绘制上的 bug，当触发虚拟键盘之后，其不会更新 fixed 定位的元素的位置。这里有几种解决方案，包括将 fixed 定位转变为 `position: absolute` 定位，或者启动一个定时器手工修正组件的位置。这些没有加入 Bootstrap 中，因此，需要由你自己选择最好的解决方案并加入到你的应用中。

### 导航条上的下拉菜单
在 iOS 设备上，由于导航组件（nav）的复杂的 z-indexing 属性，`.dropdown-backdrop` 元素并未被使用。因此，为了关闭导航条上的下拉菜单，必须直接点击下拉菜单上的元素（或者[任何其他能够触发 iOS 上 click 事件的元素](https://developer.mozilla.org/en-US/docs/Web/Events/click#Safari_Mobile)）。
### 浏览器的缩放功能
页面缩放功能不可避免的会将某些组件搞得乱七八糟，不光是 Bootstrap ，整个互联网上的所有页面都是这样。针对具体问题，我们或许可以修复它（如果有必要的话，请先搜索一下你的问题，看看是否已有解决方案，然后在向我们提交 issue）。然而，我们更倾向于忽略这些问题，由于这些问题除了一些 hack 手段，一般没有直接的解决方案。
### 移动设备上应用 :hover/:focus
尽管在大多数触摸屏上没有真正的悬停状态，大部分移动设备浏览器模拟了悬停（hover）状态并让 `:hover` 状态"多展现一会儿"。换句话说，轻触元素后开始应用 `:hover` 样式，并且在用户轻触其他的元素之后停止应用 `:hover` 样式。在这些浏览器中，Bootstrap 的 `:hover` 状态可能不是你所预期的。某些移动浏览器中的 `:focus` 状态也存在同样的问题。对于这些问题，除了完全清除这些样式，目前还没有简单的解决方法。
### Android 系统默认浏览器
Android 4.1 （甚至某些较新版本）系统的默认浏览器被设置为默认打开页面的应用程序（不同于 Chrome）。不幸的是， 一般情况下，这些浏览器有很多bug以及和CSS标准不一致的地方。
#### 选项菜单
如果 `<select>` 元素应用了 `border-radius` 和/或 `border` 样式，Android 系统默认的浏览器将不会显示侧边栏控件。（详见 [这个 StackOverflow 上的问题](https://stackoverflow.com/questions/14744437/html-select-box-not-showing-drop-down-arrow-on-android-version-4-0-when-set-with) 。） 使用下面的代码片段来删除有问题的CSS并且在Android系统默认的浏览器上，`<select>` as an呈现为无样式元素。可以通过检测用户代理（user agent）的特征串来避免干扰 Chrome、Safari和 Mozilla 浏览器。
```html
<script>
$(function () {
  var nua = navigator.userAgent
  var isAndroid = (nua.indexOf('Mozilla/5.0') > -1 && nua.indexOf('Android ') > -1 && nua.indexOf('AppleWebKit') > -1 && nua.indexOf('Chrome') === -1)
  if (isAndroid) {
    $('select.form-control').removeClass('form-control').css('width', '100%')
  }
})
</script>
```
见 [JS Bin 上的 demo](http://jsbin.com/kuvoz/1)。

## 二、jQuery
jQuery2.0.0版本没有attachEvent 所以ie8下会报错，**官方推荐ie8下使用1.12版本**

## 三、浏览器对于html5新特性的兼容

对于ie6-ie8这种老浏览器，添加 `html5shiv.js` 使其可以使用html5的特性。
```html
<!--[if lt IE 9]>
	<script src="bower_components/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
```
> * 要求把上述代码放到 `head` 标签中，**css样式的末尾**
## 零碎点：
> * js中不要使用 `const`, 用 `var` 代替
> * 判断当前浏览器是否是IE8：
```javascript
function isIE8 () {
    var browser = navigator.appName;

    var b_version = navigator.appVersion;

    var version = b_version.split(";");

    var trim_Version = version[1].replace(/[ ]/g, "");

    if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE8.0") {
        return true;
    }
    return false;
}
// 该方法目前在js/header.js中
```
> * 在ie8中无法使用CSS3中的`nth-child`, 目前使用的方式是使用jquery写代码来实现：
```javascript
$(window).resize(function () {// 当屏幕宽度变化时
      if (isIE8()) {// 判断当前浏览器是否是IE8浏览器
         var width = document.documentElement.clientWidth;
         if (width > 768) {
            $(".news-container .news-item").css("margin-right", "4%");
            $(".news-container .news-item:nth-child(2n)").css("margin-right", "0px");
         }
         if (width >= 992) {
            $(".news-container .news-item").css("margin-right", "2%");
            $(".news-container .news-item:nth-child(2n)").css("margin-right", "2%");
            $(".news-container .news-item:nth-child(3n)").css("margin-right", "0");
         }
         // if (width >= 1200) {
         //
         // }
      }
   });
```
## 五、相关链接
> * [IE8兼容性总结](https://www.cnblogs.com/yzhihao/p/6520436.html)
> * [不使用flexbox实现flexbox](https://kyusuf.com/post/almost-complete-guide-to-flexbox-without-flexbox/)



