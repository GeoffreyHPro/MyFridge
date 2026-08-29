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
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { getCalories, getMetabolismForMen, ActivityFactor, getMacros, NutritionMacros } from '../../../shared/nutrition-functions';

@Component({
  selector: 'app-nutrition-day',
  standalone: true,
  imports: [NavbarComponent, TableModule, NutritionDayMealTabAddEditRemove, Button, ChartModule, KnobModule, ReactiveFormsModule],
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

  formGroupKnobCalories!: FormGroup;
  formGroupKnobProteins!: FormGroup;
  formGroupKnobCarbohydrates!: FormGroup;
  formGroupKnobLipids!: FormGroup;

  knobCaloriesColor!: string;
  knobProteinsColor!: string;
  knobCarbohydratesColor!: string;
  knobLipidsColor!: string;

  knobCaloriesMax!: number;
  knobProteinsMax!: number;
  knobCarbohydratesMax!: number;
  knobLipidsMax!: number;

  userMacros: NutritionMacros | null = null;

  constructor(
    private nutritionDaysRepositoryService: NutritionDaysRepositoryService,
    private productsRepositoryService: ProductsRepositoryService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const calories = getCalories(getMetabolismForMen(70, 170, 28), ActivityFactor.SEDENTARY);
    const macros = getMacros(calories);
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

    // =========================
    // CALORIES
    // =========================

    const calories = this.nutritionDay.calories;
    const caloriesGoal = this.userMacros.calories;
    const caloriesRate = calories / caloriesGoal;

    this.knobCaloriesColor = this.getKnobColor(caloriesRate, 'calories');
    this.knobCaloriesMax = this.getKnobMax(calories, caloriesGoal);

    this.formGroupKnobCalories = new FormGroup({
      value: new FormControl(Math.round(calories))
    });


    // =========================
    // PROTÉINES
    // =========================

    const proteins = this.nutritionDay.proteins;
    const proteinsGoal = this.userMacros.proteins;
    const proteinsRate = proteins / proteinsGoal;

    this.knobProteinsColor = this.getKnobColor(proteinsRate, 'proteins');
    this.knobProteinsMax = this.getKnobMax(proteins, proteinsGoal);

    this.formGroupKnobProteins = new FormGroup({
      value: new FormControl(Math.round(proteins))
    });


    // =========================
    // GLUCIDES
    // =========================

    const carbohydrates = this.nutritionDay.carbohydrates;
    const carbohydratesGoal = this.userMacros.carbohydrates;
    const carbohydratesRate =
      carbohydrates / carbohydratesGoal;

    this.knobCarbohydratesColor =
      this.getKnobColor(carbohydratesRate, "carbohydrates");

    this.knobCarbohydratesMax =
      this.getKnobMax(carbohydrates, carbohydratesGoal);

    this.formGroupKnobCarbohydrates = new FormGroup({
      value: new FormControl(Math.round(carbohydrates))
    });


    // =========================
    // LIPIDES
    // =========================

    const lipids = this.nutritionDay.lipids;
    const lipidsGoal = this.userMacros.lipids;
    const lipidsRate = lipids / lipidsGoal;

    this.knobLipidsColor = this.getKnobColor(lipidsRate, 'lipids');
    this.knobLipidsMax = this.getKnobMax(lipids, lipidsGoal);

    this.formGroupKnobLipids = new FormGroup({
      value: new FormControl(Math.round(lipids))
    });
  }


  private getKnobColor(rate: number, macro: string): string {
    if (rate > 1.1) {
      return 'red';
    }

    if (rate > 1) {
      if (macro !== 'proteins') {
        return 'orange';
      } else {
        return 'green';
      }
    }

    if (rate > 0.75) {
      return 'green';
    }

    if (rate > 0.5) {
      return 'orange';
    }

    return 'red';
  }

  private getKnobMax(value: number, goal: number): number {
    if (value > goal) {
      return Math.round(value);
    }

    return goal;
  }
}
