import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TableModule } from "primeng/table";
import { UpperCasePipe } from '@angular/common';
import { MenuModule } from "primeng/menu";
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MealsRepositoryService } from '../../core/repository/meals-repository.service';

@Component({
  selector: 'app-generic-ordered-tab',
  standalone: true,
  providers: [ConfirmationService],
  imports: [TableModule, UpperCasePipe, MenuModule, FormsModule, ButtonModule, ConfirmDialogModule],
  templateUrl: './generic-ordered-tab.component.html',
  styleUrl: './generic-ordered-tab.component.css'
})
export class GenericOrderedTabComponent {
  @Input() items: any[] = [];
  @Input() itemHeaders: string[] = [];
  @Input() totalItems!: any[];
  @Input() id!: string;

  @Output() refreshMeal = new EventEmitter<string>();

  newRow: any = {};

  constructor(
    private confirmationService: ConfirmationService,
    private mealsRepositoryService: MealsRepositoryService
  ) { }

  ngOnInit(): void {
    this.resetNewRow();
  }

  resetNewRow(): void {
    this.newRow = {};

    this.itemHeaders.forEach(header => {
      this.newRow[header] = '';
    });
  }

  addRow(): void {

    this.items = [
      ...this.items,
      { ...this.newRow }
    ];

    this.resetNewRow();
  }

  editItem(): void {

  }

  deleteItem(id: string, item: any): void {

    this.confirmationService.confirm({
      message: 'Voulez-vous vraiment supprimer cet élément ?',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Oui',
      rejectLabel: 'Non',

      accept: () => {
        this.mealsRepositoryService.deleteMealItem(id, item.id).subscribe({
          next: () => {
            this.refreshMeal.emit(id);
          }
        });
        console.log('Suppression confirmée');
      },

      reject: () => {
        console.log('Suppression annulée');
      }
    });
  }

}
