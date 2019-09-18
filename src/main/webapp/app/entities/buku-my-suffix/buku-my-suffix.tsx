import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './buku-my-suffix.reducer';
import { IBukuMySuffix } from 'app/shared/model/buku-my-suffix.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBukuMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BukuMySuffix extends React.Component<IBukuMySuffixProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { bukuList, match } = this.props;
    return (
      <div>
        <h2 id="buku-my-suffix-heading">
          <Translate contentKey="etfTestApp.buku.home.title">Bukus</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="etfTestApp.buku.home.createLabel">Create a new Buku</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {bukuList && bukuList.length > 0 ? (
            <Table responsive aria-describedby="buku-my-suffix-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="etfTestApp.buku.judulBuku">Judul Buku</Translate>
                  </th>
                  <th>
                    <Translate contentKey="etfTestApp.buku.kategori">Kategori</Translate>
                  </th>
                  <th>
                    <Translate contentKey="etfTestApp.buku.mahasiswa">Mahasiswa</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {bukuList.map((buku, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${buku.id}`} color="link" size="sm">
                        {buku.id}
                      </Button>
                    </td>
                    <td>{buku.judulBuku}</td>
                    <td>{buku.kategori}</td>
                    <td>{buku.mahasiswaId ? <Link to={`mahasiswa-my-suffix/${buku.mahasiswaId}`}>{buku.mahasiswaId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${buku.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${buku.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${buku.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="etfTestApp.buku.home.notFound">No Bukus found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ buku }: IRootState) => ({
  bukuList: buku.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BukuMySuffix);
