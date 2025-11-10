import { Component, EventEmitter, Output, ViewChild, ViewEncapsulation } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Menu } from 'primeng/menu';
import { ToolbarModule } from 'primeng/toolbar';
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { MenuModule } from 'primeng/menu';
import { Button } from "primeng/button";
import { SidebarModule } from "primeng/sidebar";
import { UserRepositoryService } from '../../core/user-repository.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [ToolbarModule, MenubarModule, AvatarModule, MenuModule, Button, SidebarModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  encapsulation: ViewEncapsulation.None
})
export class NavbarComponent {
  @Output() logoutClicked = new EventEmitter<void>();
  @ViewChild('userMenu') userMenu!: Menu;

  userPseudo: string = localStorage.getItem("pseudo")!;

  constructor(private userRepositoryService: UserRepositoryService,
    private router: Router
  ) { }

  menuItems: MenuItem[] = [
    { label: 'Produits', icon: 'pi pi-box', routerLink: ['/products'] },
    { label: 'Mon frigo', icon: 'pi pi-shopping-cart', routerLink: ['/myFridge'] }
  ];

  userMenuItems: MenuItem[] = [
    {
      label: 'Se déconnecter',
      icon: 'pi pi-sign-out',
      command: () => this.userRepositoryService.logout().subscribe(() => {
        this.router.navigate(["/"]);
      })
    }
  ];

  openUserMenu(event: MouseEvent) {
    this.userMenu.toggle(event);
  }

  logout() {
    this.logoutClicked.emit();
  }
}
