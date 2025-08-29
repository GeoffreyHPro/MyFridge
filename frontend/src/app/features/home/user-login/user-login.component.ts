import { Component } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-user-login',
  standalone: true,
  imports: [FormsModule, ButtonModule],
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {
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
