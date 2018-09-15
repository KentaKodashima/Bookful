#  Bookful
![screenshots](https://user-images.githubusercontent.com/18434054/45581948-5d806e00-b85c-11e8-990a-71fd3c90e7bd.png)

## Overview
Bookful is a simple tool to keep records of the books you've read.
It is kind of a hard task to remember the contents of the books by reading just one time. Making reading records using this app might help you remember.

When you make a record. You will write the following contents.
- Title
- Author
- Description
- Review
- Book's cover image (optional)

This app is available on the [Google Play Store](https://play.google.com/store/apps/details?id=kentakodashima.com.bookful).

## Architecture
![architecture-bookful](https://user-images.githubusercontent.com/18434054/45581240-5d2da600-b84f-11e8-8ad6-71e726100aee.png)

## Database
### Realm
I adopted Realm as the database because I felt more comfortable when I handle database. There is only one RealmObject called 'Record'. The object has all the necessary properties to make a record.

![class](https://user-images.githubusercontent.com/18434054/45581250-76365700-b84f-11e8-8df2-9e29c641e358.png)

Realm has a data limit of 16MB. Therefore the RealmObject in my app holds just image data as a path in the form of String. When user uploads an image, File object is created out of random file name and getFilesDir(). Then actually writes the image using FileOutputStream. In order to re-create the images, Activity converts image String paths into File object, then decode the copied images in the file directory and set the image on ImageView.

## Library
- Realm
