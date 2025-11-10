import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { AuthGuard } from './core/guard/auth.guard';
import { ProductsComponent } from './features/products/products.component';
import { MyFridgeComponent } from './features/my-fridge/my-fridge.component';

export const routes: Routes = [
    { path: '', component: HomeComponent, /*canActivate: [LoginGuard]*/ },
    { path: 'products', component: ProductsComponent, canActivate: [AuthGuard] },
    { path: 'myFridge', component: MyFridgeComponent, canActivate: [AuthGuard] },
];
