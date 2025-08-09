import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from "primeng/toast";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonModule, ToastModule],
  providers: [MessageService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {

  constructor(private messageService: MessageService) { }

  submitForm() {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Message Content' });
  }

}
