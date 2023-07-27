# emoCloud
网抑云app--来自lyt和gyd多人开发的app
## app功能展示

![1690443619329](https://github.com/lytMoon/emoCloud/assets/119687323/ace30b74-080a-4c5d-ad24-0aa1738daba5)

![1690443878304](https://github.com/lytMoon/emoCloud/assets/119687323/e3deb8bd-20d4-4a4b-9c70-afe396bbc44a)


使用步骤：打开APP，会有一个闪屏，可以选择跳过，然后进入主页，在主页可以在推荐歌单里面寻找歌曲，如果该歌曲有mv，则在旁边就有一个播放标志，点击该标志，则可以播放mv,也可以点击上方搜索栏，进入搜索页面，搜索歌曲和mv，然后点击播放。也可以热门页面，去寻找热度较高的歌曲。点击歌曲旁的更多，就可以下载和收藏歌曲

## 在团队里所做的工作介绍

我主要负责主页面的ui设计，歌单，排行榜，热门歌手，歌曲的收藏，和下载。

大部分代码都写在一个模块里面（app模块）

### 主页面的ui设计
![1690445083413](https://github.com/lytMoon/emoCloud/assets/119687323/37c64de6-9a3f-49da-9d50-ecda41b7281a)

主界面由vp2+fragment+buttomnavigation组成，而其他的东西都放在各个fragment里面，在发现fragment里面，banner是由vp2
实现的，然后使用了Timer来实现了无限和定时轮播，推荐歌单则使用了MaskableFrameLayout实现了跑马灯的效果。在主页也实现了侧滑菜单（因为接口问题，没有实现什么功能）
### 歌单
![1690445283804](https://github.com/lytMoon/emoCloud/assets/119687323/acf4c3d3-0ddd-4921-a601-424e7f9c8745)

在点击进入歌单时使用了元素共享动画，在歌单页面则是使用了协调者布局加rv实现，在歌单里每个item的点击菜单则使用了PopupMenu

### 排行榜和热门歌手
![1690445444900](https://github.com/lytMoon/emoCloud/assets/119687323/92a140c7-f127-4a1e-9836-84f062cf1929)


### 歌曲的收藏和下载
![1690445628462](https://github.com/lytMoon/emoCloud/assets/119687323/5b882bbf-7b77-4875-82e3-060331c430a6)

歌曲的收藏，我使用SQLite数据库存储了收藏歌曲的信息，然后在收藏歌单页面再用rv把整个数据库的数据展示出来
歌曲的下载，我使用了DownloadManager来进行歌曲的本地下载，而歌曲的下载记录，我也是用的SQLite数据库存起来，然后再用一个单独的页面呈现这些信息




## 使用到的比较重要的技术及知识点

1.使用了material库中一些好看的控件

2.使用SQLite数据库来实现了收藏歌单和下载记录

3.使用了DownloadManager来实现本地下载

4.在歌单页面实现了协调者布局

5.使用了jetpack

6.使用了动画

## 不足之处

1.封装思想不到位

2.对MVVM架构的理解不到位

3.代码过于臃肿

4.ui设计不是很好看

## 心得体会

在考核过程中，我感受到了网校的学习氛围非常好，同时我也深刻意识到我自己知识的匮乏，如果我能通过这次考核，将付出更多时间学习安卓知识
