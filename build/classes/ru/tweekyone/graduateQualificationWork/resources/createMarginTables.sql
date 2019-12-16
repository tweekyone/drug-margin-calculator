
/**
 * Author:  Пирожок
 * Created: 11.12.2019
 *
 * wr - wholesale range
 * rr - retail range
 */

CREATE TABLE Region
(
    id INT NOT NULL PRIMARY KEY,
    region VARCHAR (255) NOT NULL,
    has_zone BOOLEAN
);

CREATE TABLE Zone_margin
(
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_region INT NOT NULL,
    zone VARCHAR (255),
    consist VARCHAR (255),
    wr_up_to_50 DOUBLE NOT NULL,
    wr_over_50_to_500 DOUBLE NOT NULL,
    wr_over_500 DOUBLE NOT NULL,
    rr_up_to_50 DOUBLE NOT NULL,
    rr_over_50_to_500 DOUBLE NOT NULL,
    rr_over_500 DOUBLE NOT NULL,
    CONSTRAINT fk_id_region FOREIGN KEY (id_region) REFERENCES Region(id)
)