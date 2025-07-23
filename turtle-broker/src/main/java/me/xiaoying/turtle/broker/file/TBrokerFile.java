package me.xiaoying.turtle.broker.file;

public class TBrokerFile extends SFile {
    public static String DATABASE_TYPE;

    public static String DATABASE_SQLITE_FILE;

    public static String DATABASE_MYSQL_HOST,
            DATABASE_MYSQL_DATABASE,
            DATABASE_MYSQL_USERNAME,
            DATABASE_MYSQL_PASSWORD;
    public static int DATABASE_MYSQL_PORT;

    public static String DATABASE_POSTGRESQL_HOST,
            DATABASE_POSTGRESQL_DATABASE,
            DATABASE_POSTGRESQL_USERNAME,
            DATABASE_POSTGRESQL_PASSWORD;
    public static int DATABASE_POSTGRESQL_PORT;

    public TBrokerFile() {
        super("TBroker.yml");
    }

    @Override
    public void onLoad() {
        TBrokerFile.DATABASE_TYPE = this.getConfiguration().getString("Database.Type");
        TBrokerFile.DATABASE_SQLITE_FILE = this.getConfiguration().getString("Database.Sqlite.File");

        TBrokerFile.DATABASE_MYSQL_HOST = this.getConfiguration().getString("Database.Mysql.Host");
        TBrokerFile.DATABASE_MYSQL_PORT = this.getConfiguration().getInt("Database.Mysql.Port");
        TBrokerFile.DATABASE_MYSQL_DATABASE = this.getConfiguration().getString("Database.Mysql.Database");
        TBrokerFile.DATABASE_MYSQL_USERNAME = this.getConfiguration().getString("Database.Mysql.Username");
        TBrokerFile.DATABASE_MYSQL_PASSWORD = this.getConfiguration().getString("Database.Mysql.Password");

        TBrokerFile.DATABASE_POSTGRESQL_HOST = this.getConfiguration().getString("Database.Postgresql.Host");
        TBrokerFile.DATABASE_POSTGRESQL_PORT = this.getConfiguration().getInt("Database.Postgresql.Port");
        TBrokerFile.DATABASE_POSTGRESQL_DATABASE = this.getConfiguration().getString("Database.Postgresql.Database");
        TBrokerFile.DATABASE_POSTGRESQL_USERNAME = this.getConfiguration().getString("Database.Postgresql.Username");
        TBrokerFile.DATABASE_POSTGRESQL_PASSWORD = this.getConfiguration().getString("Database.Postgresql.Password");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}