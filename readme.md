# TextViewOverflowing
TextViewOverflowing is a custom android view that allows reflowing from one TextView to another. (a la Windows 8 XAML) 

![TextView reflow example](http://i.imgur.com/9wqpuil.jpg)

### Setup 

1. Grab **TextViewOverflowing.java** and add that to your project (found under *app\src\main\java\net\justinangel\textoverflowexample\* in this repository)
2. Add a **TextViewOverflowing** and TextView to your UI. 

        <net.justinangel.textoverflowexample.TextViewOverflowing
         android:text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse id elit tempus, semper elit et, faucibus leo. Suspendisse urna urna, ornare in rutrum et, rhoncus sed risus."
         android:id="@+id/TextViewOverflowing" />

       <TextView android:text="I'm overflow text placeholder"
         android:id="@+id/overflowTextView" />

3. Hookup the TextViewOverflowing to your overflow TextView. 
      
       TextViewOverflowing textViewOverflowing = (TextViewOverflowing) findViewById(R.id.TextViewOverflowing);
       textViewOverflowing.setOverflowTextViewId(R.id.overflowTextView);

4. Run! Whenever **TextViewOverflowing** is partially hidden the remainder of the text should show up in your overflow TextView. 

### More options 

- It's possible to manually reflow text by registering to **TextViewOverflowing.setOverflowTextListener**.
        textViewOverflowing.setOverflowTextListener(new TextViewOverflowing.OverflowTextListener() {
            @Override
            public void overflowTextCalculated(String overflowText) {
                textViewOverflowing.setOverflowTextListener(null);

                overflowTextView.setText(overflowText);
            }
        });

- It's possible to chain reflow text between multiple TextViewOverflowing. 


### License 
Do whatever you'd like with this code. ("MIT license") 

### Me
 - [@JustinAngel](http://twitter.com/JustinAngel)
 - [JustinAngel.net](http://JustinAngel.net)

