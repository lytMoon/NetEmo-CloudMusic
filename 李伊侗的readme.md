# emoCloud  --李伊侗的readme

## app的介绍

这是一个由李伊侗和苟云东联合开发的app。我负责的模块是：**model_login(登录模块)** ， **model_search(搜索模块)**，**model_musicplayer(音乐播放模块)**，以及**model_mvplayer(MV播放的模块)**。

我们先来看一下整体我负责模块的情况，先来看几张图片。

p1：从app模块跳转到我的搜索模块

![1690446900253](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690446900253.gif)

p2：

![1690447021241](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690447021241.gif)



p3:



![1690447136139](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690447136139.gif)

p4:



![1690447798812](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690447798812.gif)

p5:

![1690448289818](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690448289818.gif)

p6:



![1690448368337](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690448368337.gif)



大致的我负责的板块的展示如上。我们可以通过顶部的搜索框进入我负责的搜索模块，在搜索中搜索相应的内容，下面的三个栏目“单曲”，“歌手‘，”MV“会显示相应的数据。点击单曲的每一个item可以跳转到model_musicplayer播放音乐，点击歌手的栏目可以进入查看歌手热门的50首音乐，点击MV的每一个item会跳转到model_mvplayer播放mv。

下面我们来看一下每个model的具体实现逻辑和思路。

## model的介绍

### model_login

功能图如下：![1690448983024](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690448983024.gif)


这是我第一个开始制作的模块。这个模块我个人认为是我做的最不好的一个模块，有很多的方面，我们先来看一下具体的实现逻辑。

![image-20230727171306107](C:\Users\29364\AppData\Roaming\Typora\typora-user-images\image-20230727171306107.png)

大致的分包情况如下。我们把网络请求封装在了networkUtils里面，在ui界面处理登录的具体逻辑。首先是点击生成二维码的按钮，会生成一张二维码图片，这里的具体实现是：点击按钮的时候，会进行两次网络请求，第一次拿到key，第二次通过key得到服务器返回的二维码的base64的相关信息，再通过我们自己定义的base64转成bitmap的方法，把二维码图片显示在ui上，最后用户扫码后点击登录按钮，我们会再次发送网络请求检查一下是否扫码和用户状态。这里需要做很多的逻辑处理，我用了很多的if else语句，导致代码很是膨胀，看起来有点难懂。手机号的登录方式也大致如此。这里的逻辑处理我写的很拉胯（自我感觉），但是这个模块还有下面的一些问题。

最开始写的就是**model_login**,网络请求的框架是Retrofit和Rxjava联动，最后接口返回的数据通过回调的方式传到主activity里面，其实这里我不应该写太多的接口回调，当我意识到的时候，这个模块改起来已经很麻烦了，（**除了这个登录模块，其他的模块全部删除了网络请求的接口回调**，观察者模式的本质也是接口回调。）因为登录接口返回的数据很简单，数据接受类也很简单，我**当时没有设置viewmodel层**，直接把数据返回给了主activity。当时的逻辑处理很多，重新该的话已经很麻烦了，所以我就没有在登录模块上重新翻新。很遗憾。

还有一处问题就是我tablayout和vp联动，这里的vp是vp1不是二，因为vp绑定的视图是两个xml文件，直接放在了主activity里面，这里没有设置fragment，没有使用vp和fragment联动，adapter用的是pageAdapter。

### model_search

这是我第二个开始准备的模块，当时为第一个模块耗费了很长的时间（其实完全没有必要），这个模块我个人觉得还是有一些亮点。



![1690454476884](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\1690454476884.gif)



界面比较朴素，整体的布局是实现了tablayout和vp2+fragment的联动，在主activity里面调用viewmodel中的方法保存网络请求的数据，数据存放在livedata里面（**网络请求的具体逻辑放在utils里面，viewmodel只负责接受数据，没有接口回调**），让后这里a**ctivity和fragment的通信方法我使用的viewmodel**，让fragment拿到activity 的viewmodel，viewmodel绑定的是activity，activity生命周期比fragmentc长，而且fragment要依附于activity，所以我认为没有内存泄漏的风险。

展示的列表使用的viewpager

### model_mvplayer

这是我做的第三个板块，先来看一下大致的效果图





![Screenshot_2023-07-27-18-46-46-011_com.lytredrock](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\Screenshot_2023-07-27-18-46-46-011_com.lytredrock.jpg)

播放器用的是第三方的库，ui自己搭建。

![Screenshot_2023-07-27-18-48-18-118_com.lytredrock](C:\Users\29364\Documents\Tencent Files\2936437824\FileRecv\MobileFile\Screenshot_2023-07-27-18-48-18-118_com.lytredrock.jpg)

为了实现抖音那种评论区的效果，我使用了material 的bottomsheetdialog。里面封装了fragment，不影响视频的播放。

### model_musicplayer

这个model是我最后在完成的板块，我把音乐播放放在了**Service**里面**，定义了自己的binder类来实现和activity的通信**，service的生命周期很长，并且只有一个对象，在activity启动之后会长期在后台存在，所以音乐播放应该放在这里。那么activity中的ui是怎么刷新的呢？
我使用了一个handler，定时器（在finish的时候会销毁）开了一个线程，设置了每个1秒钟执行一次任务，执行任务的具体内容便是来更新主ui的进度条。但是还有下面的问题。



![img](file:///C:\Users\29364\Documents\Tencent Files\2936437824\Image\C2C\_-724492641__101e2f6c65b60a4bd3f6b3af85ea082b_1659933538_Screenshot_2023-07-27-18-56-56-484_com.lytredrock_0_xg_0.jpg)

这里的布局是这样的：
顶部导航栏是在activity里面，中间音乐播放的界面是fragment，下面又是

我有一个效果那就是点进去第一个fragment的地方会跳转到第二个fragment，点击相关按钮还会再调过来。这里使用了动画跳转实现渐变的效果。

但是第一个fragment中旋转的图片怎么跟着暂停键一起暂停呢？这里我还是用livedata通信。livedata里面储存音乐播放器的播放状态（布尔类型的）

评论区的话还是bottomsheetdialog，这个控件是我某次在csdn上看到的，是真的好用。

歌词展示用的是rv。不过服务器那边返回的歌词需要用正则表达式加工一下。

音乐界面是我自己写的，只使用了网上的音乐播放器（media3）的播放功能。

## 技术亮点

技术亮点在每个model介绍中也提到了，下面是一个比较简陋的总结。

- 使用了MVVM框架，在ui层中进行UI的处理（不知道使用的是否规范），Viewmodel层来储存数据，网络请求具体逻辑封装到utils里面，变成单例。
- 使用了Rxjava+Retrofit进行网络请求，在新的线程进行网络请求，在主线程接收数据。
- activity和fragment之间的通信，通过共享viewmodel的方式，使用对外开放的livedata进行ui更新。fragment使用所依赖的activity的viewmodel
- 对Service有些了解，利用Service只持有一个对象的方法绑定了一些activity（导致有点bug。）使用了前台service。写了自己的binder类用于通信。
- 学习学长的初始话方法初始化了Arouter。利用Arouter在不同的模块跳转，和在同一个model中的不同activity里面跳转
- 使用handler来获取数据刷新ui



## 心得和展望

在网校学习的一年，确实让自己改变了很多，从一个对安卓开发感觉畏惧和茫然的小白变成了一个有些信心的“大白”（(●—●)），但同时因为自己对安卓的学习思路有了一点了解，对自己做app所用的东西没有太先进而感到无奈。安卓里面的一些很重要的东西自己还没有深入探索和使用。例如自定义view和协程，事件分发，滑动冲突，gradle，其他的框架等。可能是自己的认知还有点少，在自己做app开发中遇到了很多的bug也没能完美的解决。不过通过这一年的学习，自己的学习能力得到了很大的提升，感谢网校为我提供了这样的一个平台。

如果自己还有机会在这样的领域继续学习的话，接下来我准备好好学习一下一些安卓更深入的知识。

1. 再学习一下jetpack的一些其他的组件
2. 看完《Android进阶之光》
3. 深入学习协程相关的知识
4. 尝试练习一些自定义view的任务
5. 对安卓gradle了解一下
6. 学习安卓源码设计模式



这次的考核确实让自己学习到了一些知识不过，也确实看到了很多自己的不足之处。





