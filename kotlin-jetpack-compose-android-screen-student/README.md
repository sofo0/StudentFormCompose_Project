# Student Form Compose

Android app written in Kotlin with Jetpack Compose. The project contains one custom-designed screen that collects student information and validates the form before submit.

## Features

- Text fields for name, surname, and email.
- Date field opens `DatePickerDialog` and saves the selected date as `DD/MM/YYYY`.
- Radio buttons for favorite direction: Android, iOS, Web.
- Switch for agreeing to rules and conditions.
- Submit validation:
  - Empty required field, no selected option, or disabled switch: `შეავსეთ ყველა ველი!`
  - Successful submit: `მონაცემები გაიგზავნა!`

## Demo Video

Before submitting the GitHub repository, add your GIF or MP4 recording here.

Required recording flow:

1. At the beginning of the video, type your name and surname into the Name field.
2. Press Submit while fields are still empty to show the error Toast.
3. Open the calendar and select a date.
4. Fill every field, enable the agreement switch, and submit successfully.

Example Markdown after recording:

```md
![Demo](demo.gif)
```

or:

```html
<video src="demo.mp4" controls width="320"></video>
```
