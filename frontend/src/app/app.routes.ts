import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { AuthGuard } from './core/guard/auth.guard';
import { ProductsComponent } from './features/products/products.component';
import { ProductsAddEditComponent } from './features/products/products-add-edit/products-add-edit.component';
import { ProductsDetailComponent } from './features/products/products-detail/products-detail.component';
import { NutritionDayComponent } from './features/nutrition-day/nutrition-day.component';
import { NutritionDayDetailComponent } from './features/nutrition-day/nutrition-day-detail/nutrition-day-detail.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'products', component: ProductsComponent, canActivate: [AuthGuard] },
    { path: 'products/:id/detail', component: ProductsDetailComponent, canActivate: [AuthGuard] },
    { path: 'products/add', component: ProductsAddEditComponent, canActivate: [AuthGuard] },
    { path: 'nutritionDay', component: NutritionDayComponent, canActivate: [AuthGuard] },
    { path: 'nutritionDay/:id', component: NutritionDayDetailComponent, canActivate: [AuthGuard] },
];
