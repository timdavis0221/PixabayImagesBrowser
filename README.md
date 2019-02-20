# PixabayImagesBrowser

使用 Pixabay API 做一個可以搜尋圖片的 APP 來玩

## 目前的進度 :

- 能根據輸入的關鍵字來搜尋圖片，先以縮圖來呈現。

## 碰到的問題 :

1. 預設最多先抓120張圖片的 json 資訊，如果上下反覆滑動的話 APP 可能會 Crash。
2. 每個位置的圖片雖然能正確呈現，但是只要離開手機畫面再回來就會重新下載圖片再更新。

## 之後要嘗試解決的方向

1. 試著用 [Glide](https://github.com/bumptech/glide) 來解決圖片讀取跟沒有快取的問題。
2. Android 提供的 [LruCache](https://developer.android.com/reference/android/util/LruCache)
3. 判斷目前畫面中的 item 數量，只下載看得到的圖片。
4. 一次先呈現20~30個 item，並將下載好的圖片存到快取，之後滑到底部透過"讀取更多"的 item 來進行下一次的圖片讀取。
