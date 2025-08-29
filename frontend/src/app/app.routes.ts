import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { ProductsComponent } from './features/products/products.component';
import { AuthGuard } from './core/guard/auth.guard';
import { LoginGuard } from './core/guard/login.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [LoginGuard]},
    { path: 'products', component: ProductsComponent, canActivate: [AuthGuard] },
];
