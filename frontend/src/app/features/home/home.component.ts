import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from "primeng/toast";
import { UserService } from '../../core/user.service';
import { AUTHORS, TITLE } from '../../shared/constants';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { UserLoginComponent } from './user-login/user-login.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonModule, ToastModule],
  providers: [MessageService, DialogService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  title: string = TITLE;
  authors: string = AUTHORS;
  ref: DynamicDialogRef | undefined;

  constructor(
    private messageService: MessageService,
    private userService: UserService,
    private dialogService: DialogService,
    private router: Router
  ) { }

  onConnect(): void {
    this.ref = this.dialogService.open(UserLoginComponent, {
      header: "Connexion",
    })

    this.ref.onClose.subscribe((user) => {
      if (user) {
        this.userService.loginUser(user).subscribe({
          next: () => {
            this.router.navigate(["/products"])
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Connextion échouée', detail: 'Informations de compte incorrects' })
          }
        })
      }
    });
  }

}
