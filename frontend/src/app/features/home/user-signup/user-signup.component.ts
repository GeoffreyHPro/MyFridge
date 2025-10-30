import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-user-signup',
  standalone: true,
  imports: [FormsModule, ButtonModule],
  templateUrl: './user-signup.component.html',
  styleUrl: './user-signup.component.css'
})
export class UserSignupComponent {
  user = {
    pseudo: '',
    password: ''
  };

  constructor(
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig
  ) { }

  onSubmit(form: any) {
    if (form.valid) {
      this.ref.close(this.user);
    }
  }
}
