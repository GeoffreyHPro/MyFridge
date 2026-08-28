import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayRichDto, Product, ProductsRepositoryService } from '../../../core/repository/products-repository.service';
import { ActivatedRoute } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../../core/repository/nutrition-days-repository.service';
import { NutritionDayMealTabAddEditRemove } from "./nutrition-day-meal-tab-add-edit-remove/nutrition-day-meal-tab-add-edit-remove";
import { Button } from "primeng/button";

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, NutritionDayMealTabAddEditRemove, Button],
  templateUrl: './nutrition-day-detail.component.html',
  styleUrl: './nutrition-day-detail.component.css'
})
export class NutritionDayDetailComponent {
  id: string | null = "";

  nutritionDay!: NutritionDayRichDto;
  itemHeaders = ['name', 'quantity', 'calories', 'proteins', 'carbohydrates', 'lipids'];

  totalItemHeaders = ['calories', 'proteins', 'carbohydrates', 'lipids'];
  totalItemValues: number[] = []

  products: Product[] = []


  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private productsRepositoryService: ProductsRepositoryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');

    this.productsRepositoryService.getAllProducts().subscribe(products => this.products = products);

    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];
    })
  }

  refreshMeal(): void {
    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];
    })
  }
}
