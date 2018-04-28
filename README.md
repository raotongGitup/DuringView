# DuringView
# 功能介绍：
       DuringView是综合设置圆形图片，图片的双击放大和双击缩小，触摸放大缩小，安卓自定义view直线的画法，和自定义view曲线的画法，
同时还有仿照360清理加速球的画法，卫星蜘蛛网图
# 更新说明：
v1.0.0 暂时只是初步实现以上功能，后期会完善相应功能，和添加新功能
# 如何引用：
 implementation 'com.github.raotongGitup:DuringView:v.1.0.0'
 如果出现以下问题：
![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/QQ%E6%88%AA%E5%9B%BE20180428094914.png)
 请在添加以下代码：
 
![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/QQ%E6%88%AA%E5%9B%BE20180428100437.png)

# 实际效果图如下：

![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095405.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095412.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095443.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095511.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095744.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095750.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-095801.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-100014.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-100058.jpg)![Image text](https://github.com/raotongGitup/DuringView/blob/master/img-folder/S80428-100105.jpg)
# 使用方法：
# 水波纹实现的使用方法如下：

  在xml文件中添加：
  
  <com.example.duringlibrary.wavesView.LD_WaveView
       android:id="@+id/ld_waveview"
        android:layout_centerInParent="true"
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:wave_color="@color/colorAccent"
        app:wave_circle="true" />
        
 在相应的activity中添加如下代码：
 
   ldWaveView = ((LD_WaveView) findViewById(R.id.ld_waveview));
        ldWaveView.setCircle(true);
        ldWaveView.setmProgress(40);
# iamgeview圆行图片只需在xml中添加即可：
       <com.example.duringlibrary.linesAndGraphs.CircleImageView
        android:layout_centerInParent="true"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:src="@drawable/flower" />
# 蜘蛛卫星网图添加如下;
      <com.example.duringlibrary.viewCircle.RadarView
        android:layout_centerInParent="true"
        android:id="@+id/radarview"
        rv:maxRadius="95dp"
        rv:netWidth="1px"
        rv:netColor="@color/taobao_black"
        rv:rvTextColor="@color/taobao_black"
        rv:rvTextSize="8sp"
        rv:netPointColor="@color/colorPrimary"
        rv:netPointRadius="1dp"
        rv:coverColor="@color/color_cover"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
     在相应的acticity中添加：
    private List<ElementBean> mElementBeanList = new ArrayList<>();
    private RadarView radarView;
     radarView = ((RadarView) findViewById(R.id.radarview));

        mElementBeanList.add(new ElementBean("打野",20));
        mElementBeanList.add(new ElementBean("上单",70));
        mElementBeanList.add(new ElementBean("中路",90));
        mElementBeanList.add(new ElementBean("下路",30));
        mElementBeanList.add(new ElementBean("辅助",50));
        radarView.initData(mElementBeanList);
        radarView.initMAX(100);
# 双击放大图片和缩小图片实现只需在xml中添加即可,和imageview控件使用方法相同：
  
      <com.example.duringlibrary.amplificationPhoto.ZoomImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_centerInParent="true"
        android:scaleType="matrix"
        android:src="@drawable/flower" />
#  折线的应用的画法
      <HorizontalScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_centerVertical="true"
        android:background="#000"
        android:scrollbars="none">

        <com.example.duringlibrary.satelliteSpiderView.ScroolView
            android:id="@+id/scrollView_zx"
            android:layout_width="match_parent"
            android:layout_height="200dp" />
    </HorizontalScrollView>
    
    这个是直线可以左右滑动的如果不想滑动可以去掉HorizontalScrollView
    
    在activity中的用法：
    
     private ScroolView scroolView;
     private List<PointF> pointFS = new ArrayList<>();//x,y坐标数据 
     在oncreat中的用法;
     scroolView = ((ScroolView) findViewById(R.id.scrollView_zx));
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            PointF f = new PointF();
            f.x = i + "";
            f.y = random.nextInt(100);
            pointFS.add(f);
        }
        scroolView.setPointFS(pointFS);
        //以上是用的随机数，用的时候添加是哪个自己的数据即可;
      下面是介绍一些方法的而应用;
       setMax();//y的数据的最大值
       setMain()；// y的数据的最小值
       setPointFS(),//传入的数据，x的值为 string类型，y值为float类型
       setYtitle()；//y轴所表示的单位列如（km/S）
       setYtitlecolor();//y轴所表示单位的颜色
       setYtitleSize（）；// y轴所表示单位的大小；
       setXtitle（）；//x轴所表示的意义 列如（月份）
       setXtitleColor（）；// x轴所表示的意义的颜色；
       setXtitleSize（）; // x轴所表示的意义的大小；
       setYzhiTitleColor（）；// y轴值得颜色；
       setYzhiSize（）; // y轴值的大小
       setXzhiTitleColor（）;//x轴值的颜色；
       setXzhiSize（）；//x轴的大小
       setXyColor（）; // xy轴的颜色
       xyBgColor（）; // xy轴背景的十字架颜色；
       setYnumber（）；// y轴显示数据个数；
       setSolidround（）；//数据点是否显示内外圆，默认是false；
       setOutCircleColor（）;// 外圆的颜色；
       setInCirclColor（）;// 内圆的颜色
       setPointsdata（）；//是否显示每个数据点的值，默认是false
       setPointColor(); // 每个数据点的数据颜色
 

  
