import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from "primeng/toast";
import { AUTHORS, TITLE } from '../../shared/constants';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { UserLoginComponent } from './user-login/user-login.component';
import { Router } from '@angular/router';
import { UserRepositoryService } from '../../core/repository/user-repository.service';

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
    private userRepositoryService: UserRepositoryService,
    private dialogService: DialogService,
    private router: Router
  ) { }

  onConnect(): void {
    this.ref = this.dialogService.open(UserLoginComponent, {
      header: "Connexion",
    })

    this.ref.onClose.subscribe((user) => {
      if (user) {
        this.userRepositoryService.loginUser(user).subscribe({
          next: () => {
            this.router.navigate(["/products"])
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Connexion échouée', detail: 'Informations de compte incorrects' })
          }
        })
      }
    });
  }

  onSubscribe(): void {
    this.ref = this.dialogService.open(UserLoginComponent, {
      header: "Inscription",
    })

    this.ref.onClose.subscribe((user) => {
      if (user) {
        this.userRepositoryService.createUser(user).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Inscription Réussie', detail: 'Le compte a bien été créé' })
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Inscription Echouée', detail: 'Ce pseudo est déjà utilisé' })
          }
        })
      }
    });
  }
}
