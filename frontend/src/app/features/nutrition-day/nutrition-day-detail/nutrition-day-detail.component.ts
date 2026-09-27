import { Component } from '@angular/core';
import { NavbarComponent } from "../../../shared/navbar/navbar.component";
import { TableModule } from "primeng/table";
import { NutritionDayRichDto, Product, ProductsRepositoryService } from '../../../core/repository/products-repository.service';
import { ActivatedRoute } from '@angular/router';
import { NutritionDaysRepositoryService } from '../../../core/repository/nutrition-days-repository.service';
import { NutritionDayMealTabAddEditRemove } from "./nutrition-day-meal-tab-add-edit-remove/nutrition-day-meal-tab-add-edit-remove";
import { Button } from "primeng/button";
import { ChartModule } from 'primeng/chart';
import { KnobModule } from 'primeng/knob';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { getCalories, getMetabolismForMen, ActivityFactor, getMacros, NutritionMacros } from '../../../shared/nutrition-functions';
import { NutritionKnobComponent } from "../../../shared/nutrition-knob/nutrition-knob.component";

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, NutritionDayMealTabAddEditRemove, Button, ChartModule, KnobModule, ReactiveFormsModule, FormsModule, NutritionKnobComponent],
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

  userMacros!: NutritionMacros;

  knobs: Knob[] = [];

  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private productsRepositoryService: ProductsRepositoryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const calories = getCalories(getMetabolismForMen(69, 170, 28), ActivityFactor.SEDENTARY);
    const macros = getMacros(calories, 69);
    this.userMacros = macros;

    this.id = this.route.snapshot.paramMap.get('id');
    this.productsRepositoryService.getAllProducts().subscribe(products => this.products = products);

    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];

      this.updateKnobs();
    })
  }

  refreshMeal(): void {
    this.nutritionDaysRepositoryService.getNutritionDay(this.id!).subscribe(nutritionDay => {
      this.nutritionDay = nutritionDay;
      this.totalItemValues = [nutritionDay.calories, nutritionDay.proteins, nutritionDay.carbohydrates, nutritionDay.lipids];

      this.updateKnobs();
    })
  }

  private updateKnobs(): void {
    if (!this.nutritionDay || !this.userMacros) {
      return;
    }

    const macros: {
      key: keyof NutritionMacros;
      label: string;
    }[] = [
        {
          key: 'calories',
          label: 'Calories'
        },
        {
          key: 'proteins',
          label: 'Protéines'
        },
        {
          key: 'carbohydrates',
          label: 'Glucides'
        },
        {
          key: 'lipids',
          label: 'Lipides'
        }
      ];

    this.knobs = macros.map(({ key, label }) => {
      const value = this.nutritionDay[key];
      const goal = this.userMacros[key];

      return {
        key,
        label,
        value: Math.round(value),
        max: this.getKnobMax(value, goal)
      };
    });
  }


  getKnobColor(value: number, goal: number, label: string): string {
    const rate = value / goal;

    if (rate > 1.1) {
      return 'red';
    }

    if (rate > 1) {
      // Les protéines au-dessus de l'objectif restent vertes
      if (label === 'Protéines') {
        return 'green';
      }

      return 'orange';
    }

    if (rate > 0.75) {
      return 'green';
    }

    if (rate > 0.5) {
      return 'orange';
    }

    return 'red';
  }

  getKnobMax(value: number, goal: number): number {
    if (value > goal) {
      return Math.round(value);
    }

    return goal;
  }

}

interface Knob {
  key: keyof NutritionMacros;
  label: string;
  value: number;
  max: number;
}
