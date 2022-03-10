import {AbstractControl, ValidatorFn} from "@angular/forms";

export default class Validation {
  static match(controlName: string, checkFormControlName: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(controlName);
      const checkFormControl = controls.get(checkFormControlName);
      if (checkFormControl?.errors && (!checkFormControl.errors['confirmedValidator'] || checkFormControl?.touched)) {
        return null;
      }
      if (control?.value !== checkFormControl?.value) {
        controls.get(checkFormControlName)?.setErrors({ confirmedValidator: true });
        return { confirmedValidator: true };
      } else {
        return null;
      }
    };
  }
}
