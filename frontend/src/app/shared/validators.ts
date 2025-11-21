import { AbstractControl, ValidationErrors } from "@angular/forms";

export function eanValidator(control: AbstractControl): ValidationErrors | null {
    const value = (control.value || '').toString().trim();

    if (!value) return null;

    if (!/^\d+$/.test(value)) {
        return { eanInvalidChars: true };
    }

    if (![8, 13].includes(value.length)) {
        return { eanLength: true };
    }

    return null;
}