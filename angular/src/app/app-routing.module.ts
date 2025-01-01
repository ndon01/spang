import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PageNotFoundComponent} from "@core/pages/page-not-found/page-not-found.component";
import {AuthGuard} from "@core/gaurds/auth-gaurd.service";
export let routes: Routes;
routes = [
  {
    path: '',
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },

      {
        path: 'public',
        loadChildren: () =>
          import('@modules/public/public.module').then((m) => m.PublicModule),
      },

      {
        path: 'auth',
        loadChildren: () =>
          import('@modules/authentication/authentication.module').then((m) => m.AuthenticationModule),
      },
    ]
  },

  {
    path: 'login',
    redirectTo: 'auth/login',
  },

  {
    path: 'register',
    redirectTo: 'auth/register',
  },

  {
    path: 'landing',
    redirectTo: 'public/landing'
  },

  {
    path: '**',
    component: PageNotFoundComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}



