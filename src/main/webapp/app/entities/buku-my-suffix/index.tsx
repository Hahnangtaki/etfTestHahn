import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BukuMySuffix from './buku-my-suffix';
import BukuMySuffixDetail from './buku-my-suffix-detail';
import BukuMySuffixUpdate from './buku-my-suffix-update';
import BukuMySuffixDeleteDialog from './buku-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BukuMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BukuMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BukuMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={BukuMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BukuMySuffixDeleteDialog} />
  </>
);

export default Routes;
