import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { UserRepositoryService } from '../user-repository.service';

@Injectable({ providedIn: 'root' })
export class LoginGuard implements CanActivate {
  constructor(private userRepositoryService: UserRepositoryService, private router: Router) { }

  canActivate(): Observable<boolean> {
    return this.userRepositoryService.refresh().pipe(
      map(() => {
        this.router.navigate(['/products']);
        return false;
      }),
      catchError(() => {
        return of(true);
      })
    );
  }
}