####用法
```java
mChameleon = new Chameleon()
       .fromColor(ContextCompat.getColor(this, R.color.red))
       .toColor(Color.parseColor("#f9bb05"))
       .duration(2000)
       .interpolator(new LinearInterpolator())
       .setOnColorChangedListener(new Chameleon.OnColorChangedListener() {
           @Override
           public void onColorChanged(int color, boolean isChanging) {
               mBgLayout.setBackgroundColor(color);
           }
       });
mChameleon.startChangingColor();
```
![demo](https://github.com/woxingxiao/Chameleon/blob/master/demo.gif)
