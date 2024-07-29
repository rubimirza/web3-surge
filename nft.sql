create table nft_market_info
(
    id                     bigint       not null primary key comment '主键id',
    collection_id          bigint       not null comment '集合id',
    asset_id               bigint       not null comment 'nft主键',
    creator_id             bigint null comment '拥护者用户id',
    creator_wallet_address varchar(100) not null comment '拥有者钱包地址',
    nft_id                 bigint       not null comment 'nft_id',
    nft_name               varchar(100) not null comment 'NFT名称',
    nft_chain              varchar(40)  not null comment 'ethereum, polygon',
    nft_contract           varchar(100) not null comment 'NFT合约地址',
    nft_type               bigint       not null comment 'NFT类型: 721, 1155',
    nft_url                varchar(500) not null comment 'NFT图片地址',
    nft_uri                varchar(500) null comment 'NFT Metadata数据地址',
    nft_total_supply       bigint       not null comment '总数量: 721固定位1, 1155总发行数量',
    created_at             bigint       not null,
    updated_at             bigint null,
    version                bigint       not null comment '版本号'
) comment 'NFT市场的NFT';
