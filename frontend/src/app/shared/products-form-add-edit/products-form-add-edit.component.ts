import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { eanValidator } from '../validators';

@Component({
  selector: 'app-products-form-add-edit',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    InputTextModule,
    ButtonModule, MessageModule, MessagesModule, DropdownModule],
  templateUrl: './products-form-add-edit.component.html',
  styleUrl: './products-form-add-edit.component.scss'
})
export class ProductsFormAddEditComponent {
  @Output() formProduct = new EventEmitter<any>();

  form: FormGroup;

  status: DropDownOption[] = [
    { name: 'actif', code: 'ACTIF' },
    { name: 'supprimé', code: 'DELETED' }
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(10)]],
      detail: ['', [Validators.minLength(3), Validators.maxLength(100)]],
      ean: [''],
      calories: ['', [Validators.required, Validators.min(0)]],
      proteins: ['', [Validators.required, Validators.min(0)]],
      carboHydrates: ['', [Validators.required, Validators.min(0)]],
      lipids: ['', [Validators.required, Validators.min(0)]],
      status: [this.status[0], [Validators.required]]
    });
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (!control || !control.touched || control.valid) return '';

    if (control.errors?.['required']) return 'Ce champ est requis.';

    if (control.errors?.['minlength']) {
      const requiredLength = control.errors['minlength'].requiredLength;
      return `Ce champ doit comporter au minimum ${requiredLength} caractères.`;
    }

    if (control.errors?.['maxlength']) {
      const requiredLength = control.errors['maxlength'].requiredLength;
      return `Ce champ ne peut pas dépasser ${requiredLength} caractères.`;
    }

    if (control.errors?.['eanInvalidChars']) return "Ce champ doit comporter que des chiffres";

    if (control.errors?.['eanLength']) return "Ce champ doit comporter entre 8 et 13 chiffres";

    if (control.errors?.['min']) return "Ce champ doit avoir des nombres positifs";

    return '';
  }

  private emptyToNull(obj: any): any {
    const result: any = {};

    Object.keys(obj).forEach(key => {
      const value = obj[key];

      if (value === '') {
        result[key] = null;
      } else if (value && typeof value === 'object' && !Array.isArray(value)) {
        result[key] = this.emptyToNull(value);
      } else {
        result[key] = value;
      }
    });

    return result;
  }

  onSubmit() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }

    this.form.value['status'] = this.form.value['status']['code'];

    const valueWithEmpty = this.emptyToNull(this.form.value);
    this.formProduct.emit(valueWithEmpty);
  }
}

interface DropDownOption {
  name: string;
  code: string;
}
