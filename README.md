# PixabayImagesBrowser

使用 Pixabay API 做一個可以搜尋圖片的 APP 來玩

## 目前的進度 :

- 能根據輸入的關鍵字來搜尋圖片，先以縮圖來呈現。

## 解決的問題 :

1. 回收項目顯示的圖片會殘留在新的項目上，在每次 onBindViewHolder 時先設定 ImageDrawable 解決
2. 背景會下載目前畫面上看不到的圖片，在 onBindViewHolder 多設定一個 tag 攜帶要下載的任務
，並在 onViewRecycled 取消被回收掉的項目時，一併取消原本項目要進行的下載動作。

## 碰到的問題 :

1. 預設最多先抓120張圖片的 json 資訊，如果上下反覆滑動的話 APP 可能會 Crash。
2. 每個位置的圖片雖然能正確呈現，但是只要離開手機畫面再回來就會重新下載圖片再更新。
3. ~~讀取過的圖片回收後會用到新的項目上面。~~
4. ~~背景會下載目前畫面上看不到的圖片。~~

## 之後要嘗試解決的方向

1. 試著用 [Glide](https://github.com/bumptech/glide) 來解決圖片讀取跟沒有快取的問題。
2. Android 提供的 [LruCache](https://developer.android.com/reference/android/util/LruCache)
3. 判斷目前畫面中的 item 數量，只下載看得到的圖片。
4. 一次先呈現20~30個 item，並將下載好的圖片存到快取，之後滑到底部透過"讀取更多"的 item 來進行下一次的圖片讀取。
5. 讀取過的圖片要能顯示在該顯示的位置，不會重新再讀取或替換。