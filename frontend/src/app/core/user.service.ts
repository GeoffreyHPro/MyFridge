import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userRole: string = '';

  constructor() {
    const savedRole = localStorage.getItem('role');
    if (savedRole) {
      this.userRole = savedRole;
    }
  }

  setUserRole(role: string) {
    this.userRole = role;
  }

  hasRole(...roles: string[]): boolean {
    return roles.includes(this.userRole);
  }

  isAdmin(): boolean {
    return this.userRole === Role.ROLE_ADMIN
  }

  isAdminOrAgent(): boolean {
    return this.userRole === Role.ROLE_ADMIN || this.userRole === Role.ROLE_AGENT;
  }

  isAgent(): boolean {
    return this.userRole === Role.ROLE_AGENT
  }

  isUser(): boolean {
    return this.userRole === Role.ROLE_USER
  }

}

export enum Role {
  ROLE_USER = "ROLE_USER",
  ROLE_AGENT = "ROLE_AGENT",
  ROLE_ADMIN = "ROLE_ADMIN"
}
