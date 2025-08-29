import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

export interface User {
  pseudo: string;
  password: string;
}

export interface TokenResponse {
  jwt: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  createUser(user: User): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/user`, user)
  }

  loginUser(user: User): Observable<TokenResponse> {
    return this.http.post<TokenResponse>(`${this.baseUrl}/token`, user, { withCredentials: true })
      .pipe(tap((response) => {
        localStorage.setItem("role", response.role);
        localStorage.setItem("pseudo", user.pseudo);
      }));
  }

  refresh(): Observable<void> {
    return this.http.get<void>(`${this.baseUrl}/token/refresh`, { withCredentials: true })
  }

  getUserInfos(): Observable<void> {
    return this.http.get<void>(`${this.baseUrl}/user/string`, { withCredentials: true })
  }

  logout(): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/token`, { withCredentials: true })
      .pipe(tap(() => {
        localStorage.removeItem("role");
      }));
  }
}
