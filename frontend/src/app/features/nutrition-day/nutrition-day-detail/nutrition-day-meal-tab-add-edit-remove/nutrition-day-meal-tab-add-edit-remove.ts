import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { TableModule } from "primeng/table";
import { UpperCasePipe } from '@angular/common';
import { MenuModule } from "primeng/menu";
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MealItemAddCommand, MealsRepositoryService } from '../../../../core/repository/meals-repository.service';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';

@Component({
  selector: 'app-nutrition-day-meal-tab-add-edit-remove',
  standalone: true,
  providers: [ConfirmationService],
  imports: [TableModule, UpperCasePipe, MenuModule, FormsModule, ButtonModule, InputTextModule, ConfirmDialogModule, DropdownModule],
  templateUrl: './nutrition-day-meal-tab-add-edit-remove.html',
  styleUrl: './nutrition-day-meal-tab-add-edit-remove.css'
})
export class NutritionDayMealTabAddEditRemove {
  @Input() items: any[] = [];
  @Input() itemHeaders: string[] = [];
  @Input() totalItems!: any[];
  @Input() id!: string;
  @Input() products: any[] = [];

  availableProducts: any[] = [];

  @Output() refreshMeal = new EventEmitter<string>();

  newRow: any = {};
  editingItem: any = null;
  originalQuantity: number | null = null;
  addingProduct = false;

  newProduct: MealItemAddCommand = {
    productId: "",
    quantity: 1
  };

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

  editItem(item: any): void {
    this.editingItem = item;
    this.originalQuantity = item.quantity;
  }

  saveItem(id: string, item: any): void {
    console.log('Nouvelle quantité:', item.quantity);

    this.mealsRepositoryService.patchMealItem(id, item.id, item.quantity).subscribe({
      next: () => {
        this.refreshMeal.emit(id);
      }
    });

    this.editingItem = null;
    this.originalQuantity = null;
  }

  cancelEdit(): void {
    if (this.editingItem) {
      this.editingItem.quantity = this.originalQuantity;
    }

    this.editingItem = null;
    this.originalQuantity = null;
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

  addProduct() {
    console.log(this.products)
    this.products = this.products
      .filter(product => !this.items.some(item => item.id === product.id));
    this.addingProduct = true;

    this.newProduct = {
      productId: "",
      quantity: 1
    };
  }

  cancelAddProduct() {
    this.addingProduct = false;

    this.newProduct = {
      productId: "",
      quantity: 1
    };
  }

  saveNewProduct() {
    console.log(this.newProduct);

    if (!this.newProduct.quantity || this.newProduct.quantity < 1) {
      return;
    }

    this.mealsRepositoryService.addMealItem(this.id, this.newProduct).subscribe({
      next: () => {
        this.refreshMeal.emit(this.id);
      }
    });


    this.cancelAddProduct();
  }

  onProductChange(event: any): void {
    this.newProduct.productId = event.value.id;
  }
}
