import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';

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
      name: ['', [Validators.required, Validators.minLength(3)]],
      detail: ['', [Validators.required, Validators.minLength(3)]],
      ean: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(14)]],
      status: [this.status[0], [Validators.required]]
    });
  }

  getErrorMessage(field: string): string {
    const control = this.form.get(field);
    if (!control || !control.touched || control.valid) return '';

    if (control.errors?.['required']) return 'Ce champ est requis.';
    if (control.errors?.['minlength']) {
      const requiredLength = control.errors['minlength'].requiredLength;
      return `Ce champ doit contenir au moins ${requiredLength} caractères.`;
    }
    if (control.errors?.['maxlength']) {
      const requiredLength = control.errors['maxlength'].requiredLength;
      return `Ce champ ne peut pas dépasser ${requiredLength} caractères.`;
    }
    return '';
  }

  onSubmit() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }

    this.form.value['status'] = this.form.value['status']['code'];
    this.formProduct.emit(this.form.value);

  }
}

interface DropDownOption {
  name: string;
  code: string;
}
