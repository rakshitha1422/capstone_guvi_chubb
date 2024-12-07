import { Routes } from '@angular/router';
import { UserRoutingModule } from './user/user-routing.module';


export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./user/user.module').then((m) => m.UserModule),
  },
  
];
