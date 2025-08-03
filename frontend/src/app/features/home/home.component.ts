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

  myPoints = [
    { lat: 48.8566, lng: 2.3522, label: 'Paris' },
    { lat: 51.5074, lng: -0.1278, label: 'Londres' },
    { lat: 40.7128, lng: -74.0060, label: 'New York' }
  ];

  constructor(private messageService: MessageService) { }

  submitForm() {
    this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Message Content' });
  }

}
