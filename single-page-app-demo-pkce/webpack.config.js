const path = require('path');
const dotenv = require('dotenv').config({ path: path.resolve(__dirname, '.env') });
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  mode: 'development',
  entry: './src/index.ts',
  devtool: 'inline-source-map',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  plugins: [
    new webpack.DefinePlugin({
      AUTH0_DOMAIN: JSON.stringify(dotenv.AUTH0_DOMAIN),
      CLIENT_ID: JSON.stringify(dotenv.CLIENT_ID),
      REDIRECT_URL: JSON.stringify(dotenv.REDIRECT_URL),
    }),
    new HtmlWebpackPlugin({
      title: 'Development',
      template: 'src/index.html',
    }),
  ],
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  devServer: {
    contentBase: path.join(__dirname, 'dist'),
    compress: true,
    port: 3003,
  },
};
