# SimpleWheelView
A custom view using to select an item by rotating the wheel

### Version 0.5.1-RC1
### Installation
1. Add the JitPack repository to your build file
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency
```
dependencies {
  implementation 'com.github.VladChernetsky:SimpleWheelView:Tag'
}

```
## Using

### 1. Add View to your layout
```
 <com.vchernetskyi.wheelview.WheelView
        android:id="@+id/wheelViewPicker"
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:layout_gravity="center"
        app:item_text_color="@color/colorPrimary"
        app:selected_item_text_color="@color/colorAccent" />
```

#### You have to set layout height to WheelView. It can be set as a dimen or style.

### 2. Provide list of WheelItem's

```
wheelViewPicker.submitItems(list)
```

### 3. Add listener to cache selected items
```
wheelViewPicker.setOnWheelItemSelectListener(object : WheelView.OnWheelViewItemSelectListener {
            override fun onItemSelected(item: WheelItem) {
                Toast.makeText(applicationContext, item.toString(), Toast.LENGTH_SHORT).show()
            }
        })
```

### 4. Set default item by id
```
wheelViewPicker.selectItemById(3)
```
