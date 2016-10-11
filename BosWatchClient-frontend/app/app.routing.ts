import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AlarmsComponent }      from './pocsag/alarms.component';
import { FmsComponent }      from './fms/fms.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/alarms',
        pathMatch: 'full'
    },  {
        path: 'alarms',
        component: AlarmsComponent
    }, {
        path: 'alarm/:id',
        component: AlarmsComponent
    },  {
        path: 'fms',
        component: FmsComponent
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
