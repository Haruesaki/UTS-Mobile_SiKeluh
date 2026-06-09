# Walkthrough - Password Visibility Toggle

I have added a password visibility toggle (eye icon) to the Login and Register screens. This allows users to see or hide their password while typing.

## Changes

### [AuthComponents.kt](file:///C:/Kuliah/coding/Android Studio/SiKeluh/app/src/main/java/com/example/sikeluh/ui/components/AuthComponents.kt)

Modified `AuthTextField` to include:
- A `passwordVisible` state variable.
- A `trailingIcon` that toggles the visibility state.
- A `visualTransformation` that switches between `PasswordVisualTransformation()` and `VisualTransformation.None` based on the visibility state.

## Verification Summary

### Manual Verification
- Rendered a Compose Preview of `AuthTextField` with `isPassword = true`.
- Confirmed that the eye-off icon (`VisibilityOff`) is displayed by default and the text is obscured.

![AuthTextField Password Preview](preview_password_field.png)
*(Note: I've saved the preview image as preview_password_field.png in the artifacts directory for reference)*
