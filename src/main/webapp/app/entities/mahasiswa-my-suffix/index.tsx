import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MahasiswaMySuffix from './mahasiswa-my-suffix';
import MahasiswaMySuffixDetail from './mahasiswa-my-suffix-detail';
import MahasiswaMySuffixUpdate from './mahasiswa-my-suffix-update';
import MahasiswaMySuffixDeleteDialog from './mahasiswa-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MahasiswaMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MahasiswaMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MahasiswaMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={MahasiswaMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MahasiswaMySuffixDeleteDialog} />
  </>
);

export default Routes;
