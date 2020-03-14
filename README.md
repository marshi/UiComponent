# Shard
Android ui component library.

# UI Component

## ExpandableItemView

Parent view of DividerView and ExpandPartView.

This is google phone app like ui.

<img src="https://user-images.githubusercontent.com/1423942/76684762-feb6d300-6651-11ea-88b1-b97478ff19e7.gif" width="200" />

### custom attribute

* app:duration

expand and collapse animation duration. optional.

default 200ms.

### DividerView

Optional.

Child view of ExpandableItemView.

If you want to show divider, use this.

#### custom attribute

### ExpandPartView

Required.

Child view of ExpandableItemView, and parent view of views that expand and collapse.

#### custom attribute
* app:expand_height

height after expanded.

required.

### How to use 
```xml
<dev.marshi.android.shard.expandableview.ExpandableItemView
    android:id="@+id/root_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginStart="10dp"
    android:layout_marginEnd="10dp"
    android:background="@drawable/expandable_item_shape"
    app:duration="200"
    app:elevationTo="@dimen/item_elevation">

    <TextView
        android:id="@+id/text"
        style="@style/ExpandableItemUpperView"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:gravity="center_vertical"
        android:paddingStart="10dp"
        android:paddingEnd="10dp"
        android:text="あいうえおあいうえおあいうえお"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <dev.marshi.android.shard.expandableview.DividerView
        android:id="@+id/divider"
        style="@style/ExpandableItemDivider"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:visibility="invisible"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/text" />

    <dev.marshi.android.shard.expandableview.ExpandPartView
        android:id="@+id/expand_constraint"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:expand_height="@dimen/expand_height"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/text"
        tools:layout_height="40dp">

        <TextView
            android:id="@+id/expand"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="expand" />
    </dev.marshi.android.shard.expandableview.ExpandPartView>

</dev.marshi.android.shard.expandableview.ExpandableItemView>
```
